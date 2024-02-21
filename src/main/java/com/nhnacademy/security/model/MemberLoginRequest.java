package com.nhnacademy.security.model;

import lombok.Data;

@Data
public class MemberLoginRequest {
    private final String name;
    private final String pwd;

}
