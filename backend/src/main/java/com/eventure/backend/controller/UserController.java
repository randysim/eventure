package com.eventure.backend.controller;

import com.eventure.backend.model.Plan;
import com.eventure.backend.model.User;
import com.eventure.backend.security.GoogleOAuth2User;
import com.eventure.backend.service.PlanService;
import com.eventure.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final UserService userService;
    private final PlanService planService;

    @Autowired
    public UserController(UserService userService, PlanService planService) {
        this.userService = userService;
        this.planService = planService;
    }

    @GetMapping(path = "/authenticated")
    public User getAuthenticatedUser(@AuthenticationPrincipal GoogleOAuth2User principal) {
        return userService.getAuthenticatedUser(principal);
    }

    @GetMapping(path = "/plans")
    public List<Plan> getPlans(@AuthenticationPrincipal GoogleOAuth2User principal) {
        return planService.getUserPlans(principal.getEmail());
    }
}