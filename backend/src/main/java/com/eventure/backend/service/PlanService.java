package com.eventure.backend.service;

import com.eventure.backend.dto.inbound.PlanRequestDTO;
import com.eventure.backend.dto.outbound.PlanResponseDTO;
import com.eventure.backend.model.Day;
import com.eventure.backend.model.Plan;
import com.eventure.backend.model.Step;
import com.eventure.backend.model.User;
import com.eventure.backend.repository.DayRepository;
import com.eventure.backend.repository.PlanRepository;
import com.eventure.backend.repository.StepRepository;
import com.eventure.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/* Note: Plan serializes all the children, so when getting and updating must include all children */
@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final DayRepository dayRepository;
    private final StepRepository stepRepository;
    private static final Logger log = LoggerFactory.getLogger(PlanService.class);

    @Autowired
    public PlanService(
            PlanRepository planRepository,
            UserRepository userRepository,
            DayRepository dayRepository,
            StepRepository stepRepository
    ) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
        this.dayRepository = dayRepository;
        this.stepRepository = stepRepository;
    }

    /* Get User Plans */
    public List<PlanResponseDTO> getUserPlans(String userEmail) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        User user = userOptional.get();
        return user.getPlans()
                .stream()
                .map(plan -> new PlanResponseDTO(plan.getId(), plan.getTitle(), plan.getNotes()))
                .collect(Collectors.toList());
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
    public PlanResponseDTO createPlan(String userEmail, Plan planDTO) {
        if (planDTO.getTitle().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title cannot be empty.");
        }

        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        Plan plan = new Plan(planDTO.getTitle(), planDTO.getNotes(), false);

        User user = userOptional.get();
        user.addPlan(plan);
        plan.setNotes("");
        plan.setCreatedAt(LocalDate.now());
        plan.setUpdatedAt(LocalDate.now());

        planRepository.save(plan);
        return new PlanResponseDTO(plan.getId(), plan.getTitle(), plan.getNotes());
    }

    /* Update plan by id */
    public Plan updatePlan(String userEmail, Long id, Plan planDTO) {
        Optional<Plan> planOptional = planRepository.findById(id);
        if (planOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found.");
        }

        Plan plan = planOptional.get();
        if (!plan.getUser().getEmail().equals(userEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized to update this plan.");
        }

        plan.getDays().clear();

        for (Day day : planDTO.getDays()) {
            Day savedDay;

            if (day.getId() == null) {
                savedDay = new Day(day.getOrder());
            } else {
                Optional<Day> dayOptional = dayRepository.findById(day.getId());

                if (dayOptional.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Day not found.");
                }

                savedDay = dayOptional.get();
                savedDay.setOrder(day.getOrder());
            }

            savedDay.getSteps().clear();
            for (Step step : day.getSteps()) {
                Step savedStep;

                if (step.getId() == null) {
                    savedStep = step;
                } else {
                    Optional<Step> stepOptional = stepRepository.findById(step.getId());
                    if (stepOptional.isEmpty()) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Step not found.");
                    }

                    savedStep = stepOptional.get();
                }

                savedStep.setOrder(step.getOrder());
                savedStep.setDescription(step.getDescription());
                savedStep.setStart(step.getStart());
                savedStep.setEnd(step.getEnd());
                stepRepository.save(savedStep);
                savedDay.getSteps().add(savedStep);
            }

            dayRepository.save(savedDay);

            plan.getDays().add(savedDay);
        }

        plan.setUpdatedAt(LocalDate.now());
        plan.setTitle(planDTO.getTitle());
        plan.setNotes(planDTO.getNotes());

        planRepository.save(plan);

        return plan;
    }

    /* delete plan by id */
    public void deletePlan(String userEmail, Long id) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        User user = userOptional.get();
        Optional<Plan> planOptional = planRepository.findById(id);

        if (planOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found.");
        }

        Plan plan = planOptional.get();

        if (!plan.getUser().getEmail().equals(userEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized to delete this plan.");
        }

        user.removePlan(plan);
        userRepository.save(user);
    }
}
