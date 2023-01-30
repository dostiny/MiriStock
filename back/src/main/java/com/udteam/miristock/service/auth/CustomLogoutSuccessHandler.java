package com.udteam.miristock.service.auth;

import com.udteam.miristock.util.HeaderUtil;
import com.udteam.miristock.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final RedisUtil redisUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String accesstoken = HeaderUtil.getAccessToken(request);
        log.info("Logout access_token = {}",accesstoken);
        //true 일 경우 엑세스 토큰 블랙리스트에 등록되있는 상태
        redisUtil.setDataExpire(accesstoken, "true", 1000L * 60L * 10L);
        log.info("set Blacklist = {}",redisUtil.getData(accesstoken));
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/");
    }
}
