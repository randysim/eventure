package com.eventure.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDate;

@Entity
@Table(name = "step")
public class Step {
    @Id
    @SequenceGenerator(
            name = "step_sequence",
            sequenceName = "step_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "step_sequence"
    )
    private Long id;
    private String description;
    private int order;
    private LocalDate startTime;
    private LocalDate endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id")
    @JsonBackReference
    private Day day;

    public Step() {}
    public Step(String description, int order, LocalDate startTime, LocalDate endTime) {
        this.description = description;
        this.order = order;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
