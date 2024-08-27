package com.eventure.backend.dto.outbound;

import java.time.LocalDate;

public class PlanResponseDTO {
    private Long id;
    private String title;
    private String notes;
    private LocalDate updatedAt;

    public PlanResponseDTO(Long id, String title, String notes, LocalDate updatedAt) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
