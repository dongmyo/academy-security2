package com.nhnacademy.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminOrMemberService {
    // TODO #2: 실습 - 어드민이나 멤버만 이 메서드를 실행할 수 있도록 설정하세요.
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MEMBER')")
    public String getLinkOnlyAdminOrMemberCanAccess() {
        return "https://www.youtube.com/watch?v=otCpCn0l4Wo";
    }

}
