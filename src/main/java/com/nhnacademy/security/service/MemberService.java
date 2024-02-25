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
        // TODO #3: PasswordEncoder 를 통해 비밀번호 hash 값 생성해서 데이터베이스에 저장.
        String hash = passwordEncoder.encode(request.getPwd());

        Member member = Member.forCreate(request.getName(), hash, request.getAuthority());
        memberRepository.saveAndFlush(member);

        return new MemberResponse(member.getId(), member.getName());
    }

    public MemberResponse processLogin(MemberLoginRequest request) throws LoginFailureException {
        Member member = memberRepository.findByName(request.getName());
        if (Objects.isNull(member)) {
            throw new LoginFailureException(request.getName());
        }

        // 권한 체크
        String authority = Optional.ofNullable(member.getAuthority())
                                   .map(Authority::getAuthority)
                                   .orElse(null);

        if (Objects.isNull(authority) || !"member".equals(authority)) {
            throw new LoginFailureException(request.getName());
        }

        if (!comparePassword(request.getPwd(), member.getPassword())) {
            throw new LoginFailureException(request.getName());
        }

        return new MemberResponse(member.getId(), member.getName());
    }

    private boolean comparePassword(String rawPassword, String encodedPassword) {
        byte[] salt = PasswordUtils.hexToBytes(encodedPassword.substring(0, 16));

        String hash = encodePassword(rawPassword, salt);

        return hash.equals(encodedPassword);
    }

    private String encodePassword(String rawPassword, byte[] salt) {
        return PasswordUtils.bytesToHex(salt) + PasswordUtils.encode(rawPassword, salt);
    }

}
