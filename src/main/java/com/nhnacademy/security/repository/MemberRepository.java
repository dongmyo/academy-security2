package com.nhnacademy.security.repository;

import com.nhnacademy.security.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByNameAndPassword(String name, String password);

}
