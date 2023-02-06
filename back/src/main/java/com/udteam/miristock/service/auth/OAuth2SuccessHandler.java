package com.udteam.miristock.service.auth;

import com.udteam.miristock.entity.MemberEntity;
import com.udteam.miristock.entity.Role;
import com.udteam.miristock.repository.MemberRepository;
import com.udteam.miristock.repository.RedisRepository;
import com.udteam.miristock.util.CookieUtil;
import com.udteam.miristock.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenservice;
    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;
    private final RedisRepository redisRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        log.info("Principal에서 꺼낸 OAuth2User = {} ", oAuth2User);

        log.info("DB 등록 확인");

        // 등록되지 않은 회원일경우 회원가입
        MemberEntity member = memberRepository.findByMemberEmail((String) oAuth2User.getAttribute("email"));
        if (oAuth2User != null && member == null) {
            log.info("유저를 찾을 수 없습니다. 유저 정보를 등록합니다.");
            createMember(oAuth2User);
        }

        // 다른 소셜로 등록된 회원이면 error페이지 리턴
        if (member != null && !member.getMemberProvider().equals(oAuth2User.getAttribute("provider"))) {
            log.info("다른 소셜에 등록된 회원입니다");
            getRedirectStrategy().sendRedirect(request, response, UriComponentsBuilder.fromUriString("/error")
                    .build().toUriString());
            return;
        }
        String nickname = memberRepository.findByMemberEmail((String) oAuth2User.getAttribute("email")).getMemberNickname();
        log.info("nickname = {}", nickname);

        // 만약 해당 이메일로 리프레쉬 토큰이 존재한다면 삭제
        if (redisUtil.getData((String) oAuth2User.getAttribute("email")) != null) {
            log.info("refresh token exists.Remove refresh token");
            redisRepository.deleteById((String) oAuth2User.getAttribute("email"));
        }

        // 리프레쉬 토큰 생성 후 Redis에 등록
        String refreshtoken = tokenservice.generateToken((String) oAuth2User.getAttribute("email"), "ROLE_MEMBER", nickname, "REFRESH");
        redisUtil.setDataExpire((String) oAuth2User.getAttribute("email"), refreshtoken, TokenService.refreshPeriod);

        // Response Cookie에 리프레쉬 토큰 적재, access token 생성
        ResponseCookie cookie = cookieUtil.getCookie(refreshtoken, TokenService.refreshPeriod);
        String accesstoken = tokenservice.generateToken(oAuth2User.getAttribute("email"), "ROLE_MEMBER", nickname, "ACCESS");

        log.info("accecss_Token = {}", accesstoken);
        log.info("refresh_Token = {}", refreshtoken);
        response.setContentType("application/json;charset=UTF-8");
        // Authorization 헤더필드에 accesstoken 적재 , Set-Cookkie 헤더 필드에 리프레쉬 토큰 cookie 적재
        response.setHeader("Set-Cookie", cookie.toString());

        // 닉네임 작성 여부 확인
        if (nickname == null) {
            // 닉네임 설정 화면으로
            log.info("닉네임 설정 화면으로");
            getRedirectStrategy().sendRedirect(request, response, UriComponentsBuilder.fromUriString("http://localhost:3000/redirect")
                    .queryParam("accesstoken", accesstoken)
                    .build().toUriString());
        } else {
            // 메인으로
            log.info("메인으로");
            getRedirectStrategy().sendRedirect(request, response, UriComponentsBuilder.fromUriString("http://localhost:3000/redirect")
                    .queryParam("accesstoken", accesstoken)
                    .build().toUriString());
        }
    }

    private MemberEntity createMember(OAuth2User oAuth2User) {
        return memberRepository.saveAndFlush(MemberEntity.builder()
                .memberEmail((String) oAuth2User.getAttribute("email"))
                .role(Role.MEMBER)
                .memberProvider(oAuth2User.getAttribute("provider"))
                .build());
    }
}
