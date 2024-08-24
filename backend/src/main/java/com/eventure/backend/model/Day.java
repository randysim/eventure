package com.eventure.backend.model;

import jakarta.persistence.*;

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

    @Column(name = "plan_order")
    private int order;

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
}
