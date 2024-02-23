package com.nhnacademy.security.service;

import com.nhnacademy.security.entity.Authority;
import com.nhnacademy.security.entity.Member;
import com.nhnacademy.security.exception.LoginFailureException;
import com.nhnacademy.security.model.MemberCreateRequest;
import com.nhnacademy.security.model.MemberLoginRequest;
import com.nhnacademy.security.model.MemberResponse;
import com.nhnacademy.security.repository.MemberRepository;
import com.nhnacademy.security.util.PasswordUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {
        // TODO #1: 비밀번호를 plain text 로 저장하지 말고 hash 값을 저장.
        byte[] salt = new byte[8];

        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        String hash = encodePassword(request.getPwd(), salt);

        Member member = Member.forCreate(request.getName(), hash);
        memberRepository.saveAndFlush(member);

        return new MemberResponse(member.getId(), member.getName());
    }

    public MemberResponse processLogin(MemberLoginRequest request) throws LoginFailureException {
        // TODO #2: password 를 사용자 입력값과 직접 비교하면 안 됨.
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

        // TODO #3: 사용자가 입력한 비밀번호의 hash 값과 데이터베이스에 저장된 hash 값을 비교.
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
