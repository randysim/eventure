package com.eventure.backend.model;

import jakarta.persistence.*;

import java.sql.Array;
import java.util.ArrayList;
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

    @Column(name = "day_order")
    private int order;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id", referencedColumnName = "id")
    private List<Step> steps = new ArrayList<>();

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

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
