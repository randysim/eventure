package com.eventure.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "day")
public class Day {
    @Id
    @SequenceGenerator(
            name = "day_sequence",
            sequenceName = "day_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "day_sequence"
    )
    private Long id;
    private int order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps;

    public Day() {}
    public Day(int order) {
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void addStep(Step step) {
        step.setDay(this);
        steps.add(step);
    }

    public void removeStep(Step step) {
        steps.remove(step);
        step.setDay(null);
    }

    public List<Step> getSteps() {
        return steps;
    }
}
