package com.nhnacademy.security.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "Authorities")
public class Authority {
    @Id
    private Long id;

    private String authority;

    @MapsId
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Authority() {
        // nothing
    }

    public static Authority forCreate(String authority, Member member) {
        Authority entity = new Authority();
        entity.authority = authority;
        entity.member = member;

        return entity;
    }

}
