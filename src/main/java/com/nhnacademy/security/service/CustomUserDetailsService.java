package com.nhnacademy.security.service;

import com.nhnacademy.security.entity.Member;
import com.nhnacademy.security.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(username);
        if (Objects.isNull(member)) {
            throw new UsernameNotFoundException(username + " not found");
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().getAuthority());
        Collection<GrantedAuthority> grantedAuthorities = Collections.singletonList(grantedAuthority);

        return new User(member.getName(), member.getPassword(), grantedAuthorities);
    }

}
