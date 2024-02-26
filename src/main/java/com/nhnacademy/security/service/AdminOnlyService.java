package com.nhnacademy.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminOnlyService {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getDataOnlyAdminCanAccess() {
        return "employee salary list in excel";
    }

}
