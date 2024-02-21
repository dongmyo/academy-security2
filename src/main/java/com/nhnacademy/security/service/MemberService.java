package com.nhnacademy.security.service;

import com.nhnacademy.security.entity.Member;
import com.nhnacademy.security.model.MemberCreateRequest;
import com.nhnacademy.security.model.MemberResponse;
import com.nhnacademy.security.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {
        Member member = Member.forCreate(request.getName(), request.getPwd());
        memberRepository.saveAndFlush(member);

        return new MemberResponse(member.getId(), member.getName());
    }

}
