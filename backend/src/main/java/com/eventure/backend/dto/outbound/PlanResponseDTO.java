package com.eventure.backend.dto.outbound;

public class PlanResponseDTO {
    private Long id;
    private String title;
    private String notes;

    public PlanResponseDTO(Long id, String title, String notes) {
        this.id = id;
        this.title = title;
        this.notes = notes;
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
}
