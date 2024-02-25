package com.nhnacademy.security.entity;

import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "Members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(name = "pwd")
    private String password;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Authority authority;


    public Member() {
        // nothing
    }

    public static Member forCreate(String name, String password, String authority) {
        Member member = new Member();
        member.name = name;
        member.password = password;
        member.authority = Authority.forCreate(authority, member);

        return member;
    }

}
