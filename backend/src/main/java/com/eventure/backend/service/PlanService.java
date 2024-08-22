package com.eventure.backend.service;

import com.eventure.backend.model.Plan;
import com.eventure.backend.model.User;
import com.eventure.backend.repository.PlanRepository;
import com.eventure.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/* Note: Plan serializes all the children, so when getting and updating must include all children */
@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    @Autowired
    public PlanService(PlanRepository planRepository, UserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    /* Get User Plans */
    public List<Plan> getUserPlans(String userEmail) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        User user = userOptional.get();
        return user.getPlans();
    }

    /* Get Plan By ID */
    public Plan getPlan(String userEmail, Long id) {
        Optional<Plan> planOptional = planRepository.findById(id);

        if (planOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found.");
        }

        Plan plan = planOptional.get();

        if (!plan.getUser().getEmail().equals(userEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized to view this plan.");
        }

        return plan;
    }

    /* Create plan */
    public Plan createPlan(String userEmail, Plan plan) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        User user = userOptional.get();
        user.addPlan(plan);

        planRepository.save(plan);
        return plan;
    }

    /* Update plan by id */
    public Plan updatePlan(String userEmail, Long id, Plan newPlan) {
        Optional<Plan> planOptional = planRepository.findById(id);
        if (planOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found.");
        }

        Plan plan = planOptional.get();
        if (!plan.getUser().getEmail().equals(userEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized to update this plan.");
        }

        plan.setUpdatedAt(LocalDate.now());
        plan.setTitle(newPlan.getTitle());
        plan.setNotes(newPlan.getNotes());
        plan.setTodoList(newPlan.getTodoList());
        plan.setDays(newPlan.getDays());

        planRepository.save(plan);

        return plan;
    }

    /* delete plan by id */
    public void deletePlan(String userEmail, Long id) {
        Optional<Plan> planOptional = planRepository.findById(id);
        if (planOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found.");
        }

        Plan plan = planOptional.get();
        if (!plan.getUser().getEmail().equals(userEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized to delete this plan.");
        }

        planRepository.delete(plan);
    }
}
