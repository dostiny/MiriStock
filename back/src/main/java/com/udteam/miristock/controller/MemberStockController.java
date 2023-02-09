package com.udteam.miristock.controller;

import com.udteam.miristock.dto.MemberAssetDto;
import com.udteam.miristock.service.MemberAssetService;
import com.udteam.miristock.util.ErrorMessage;
import com.udteam.miristock.dto.MemberDto;
import com.udteam.miristock.dto.MemberStockDto;
import com.udteam.miristock.service.MemberService;
import com.udteam.miristock.service.MemberStockService;
import com.udteam.miristock.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/asset/memberstock")
@RequiredArgsConstructor
public class MemberStockController {

    private final MemberStockService memberStockService;
    private final MemberService memberService;
    private final MemberAssetService memberAssetService;

    
    // 보유 주식 리스트 들고오면서 현재 가격 같이 들고오기
    @GetMapping()
    public ResponseEntity<?> findAll(@RequestHeader String Authorization, @RequestParam String type) {
        MemberDto m = memberService.selectOneMember(HeaderUtil.getAccessTokenString(Authorization));
        if (m == null){
            log.info(ErrorMessage.TOKEN_EXPIRE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            MemberAssetDto result = memberAssetService.selectMemberAsset(m.getMemberNo());
            return ResponseEntity.ok().body(memberStockService.findAll(m.getMemberNo(), result.getMemberassetCurrentTime(), type));
        }
    }

    // 보유 주식 단건 검색
    @GetMapping("/{stockCode}")
    public ResponseEntity<?> findOne(@RequestHeader String Authorization, @PathVariable String stockCode) {
        MemberDto m = memberService.selectOneMember(HeaderUtil.getAccessTokenString(Authorization));
        if (m == null){
            log.info(ErrorMessage.TOKEN_EXPIRE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            MemberAssetDto result = memberAssetService.selectMemberAsset(m.getMemberNo());
            return ResponseEntity.ok().body(memberStockService.findOne(m.getMemberNo(), result.getMemberassetCurrentTime(), stockCode).get(0));
        }
    }

    // create test
    @PostMapping
    public ResponseEntity<MemberStockDto> save(@RequestHeader String Authorization, @RequestBody MemberStockDto memberStockDto) {
        MemberDto m = memberService.selectOneMember(HeaderUtil.getAccessTokenString(Authorization));
        if (m == null){
            log.info(ErrorMessage.TOKEN_EXPIRE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            memberStockDto.setMemberNo(m.getMemberNo());
            return ResponseEntity.ok().body(memberStockService.save(memberStockDto));
        }
    }

    // update test
    @PutMapping
    public ResponseEntity<MemberStockDto> update(@RequestHeader String Authorization, @RequestBody MemberStockDto memberStockDto) {
        MemberDto m = memberService.selectOneMember(HeaderUtil.getAccessTokenString(Authorization));
        if (m == null){
            log.info(ErrorMessage.TOKEN_EXPIRE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            memberStockDto.setMemberNo(m.getMemberNo());
            return ResponseEntity.ok().body(memberStockService.save(memberStockDto));
        }
    }

    // delete test
    @DeleteMapping
    public ResponseEntity<MemberStockDto> delete(@RequestHeader String Authorization, @RequestParam String stockCode) {
        MemberDto m = memberService.selectOneMember(HeaderUtil.getAccessTokenString(Authorization));
        if (m == null){
            log.info(ErrorMessage.TOKEN_EXPIRE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            memberStockService.delete(m.getMemberNo(), stockCode);
            return ResponseEntity.ok().body(null);
        }
    }

}
