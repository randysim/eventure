package com.eventure.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
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

    @Column(name = "step_order")
    private Long order;

    private String description;

    @Column(name = "start_time")
    private LocalTime start;

    @Column(name = "end_time")
    private LocalTime end;

    public Step() { }
    public Step(Long order, String description, LocalTime start, LocalTime end) {
        this.order = order;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
