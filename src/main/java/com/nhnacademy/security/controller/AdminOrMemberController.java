package com.nhnacademy.security.controller;

import com.nhnacademy.security.service.AdminOrMemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// TODO #1: 어드민이나 멤버만 접근 가능한 컨트롤러.
@Controller
@RequestMapping("/admin-or-member")
public class AdminOrMemberController {
    private final AdminOrMemberService adminOrMemberService;

    public AdminOrMemberController(AdminOrMemberService adminOrMemberService) {
        this.adminOrMemberService = adminOrMemberService;
    }

    @GetMapping
    public String adminOrMember(Model model) {
        model.addAttribute("link", adminOrMemberService.getLinkOnlyAdminOrMemberCanAccess());

        return "admin-or-member";
    }

}
