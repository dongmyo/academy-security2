package com.nhnacademy.security.service;

import com.nhnacademy.security.entity.Authority;
import com.nhnacademy.security.entity.Member;
import com.nhnacademy.security.exception.LoginFailureException;
import com.nhnacademy.security.model.MemberCreateRequest;
import com.nhnacademy.security.model.MemberLoginRequest;
import com.nhnacademy.security.model.MemberResponse;
import com.nhnacademy.security.repository.MemberRepository;
import com.nhnacademy.security.util.PasswordUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public MemberService(MemberRepository memberRepository,
                         PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {
        String hash = passwordEncoder.encode(request.getPwd());

        Member member = Member.forCreate(request.getName(), hash, request.getAuthority());
        memberRepository.saveAndFlush(member);

        return new MemberResponse(member.getId(), member.getName());
    }

}
