package com.eventure.backend.controller;

import com.eventure.backend.dto.inbound.PlanRequestDTO;
import com.eventure.backend.dto.outbound.PlanResponseDTO;
import com.eventure.backend.model.Plan;
import com.eventure.backend.security.GoogleOAuth2User;
import com.eventure.backend.service.PlanService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/plans")
public class PlanController {
    private final PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping(path = "/{planId}")
    public Plan getPlan(@AuthenticationPrincipal GoogleOAuth2User principal, @PathVariable Long planId){
        return planService.getPlan(principal.getEmail(), planId);
    }

    @PostMapping
    @Transactional
    public PlanResponseDTO createPlan(@AuthenticationPrincipal GoogleOAuth2User principal, @RequestBody Plan planDTO){
        return planService.createPlan(principal.getEmail(), planDTO);
    }

    @PutMapping(path = "/{planId}")
    public Plan updatePlan(@AuthenticationPrincipal GoogleOAuth2User principal, @PathVariable Long planId, @RequestBody Plan plan){
        return planService.updatePlan(principal.getEmail(), planId, plan);
    }
}
