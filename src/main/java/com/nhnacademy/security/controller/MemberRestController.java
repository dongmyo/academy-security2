package com.nhnacademy.security.controller;

import com.nhnacademy.security.model.MemberCreateRequest;
import com.nhnacademy.security.model.MemberResponse;
import com.nhnacademy.security.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping("/members")
    public MemberResponse createMember(@RequestBody MemberCreateRequest request) {
        return memberService.createMember(request);
    }

}
