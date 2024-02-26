package com.nhnacademy.security.service;

import org.springframework.stereotype.Service;

@Service
public class AdminOnlyService {
    // TODO : #6 이제 해당 메서드에 @PreAuthorize 를 걸면 ADMIN만 접근 가능하도록 수정 가능.
    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getDataOnlyAdminCanAccess() {
        return "employee salary list in excel";
    }

}
