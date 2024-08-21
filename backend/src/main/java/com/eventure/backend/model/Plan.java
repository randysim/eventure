package com.eventure.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "plan")
public class Plan {
    @Id
    @SequenceGenerator(
            name = "plan_sequence",
            sequenceName = "plan_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "plan_sequence"
    )
    private Long id;
    private String title;
    private String notes;
    private Boolean isPublic;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Day> days;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "todolist_id", referencedColumnName = "id")
    @JsonManagedReference
    private TodoList todoList;

    public Plan() {}
    public Plan(String title, String notes, Boolean isPublic) {
        this.title = title;
        this.notes = notes;
        this.isPublic = isPublic;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void addDay(Day day) {
        day.setPlan(this);
        this.days.add(day);
    }

    public void removeDay(Day day) {
        this.days.remove(day);
        day.setPlan(null);
    }

    public List<Day> getDays(Day day) {
        return this.days;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
        todoList.setPlan(this);
    }

    public TodoList getTodoList() {
        return this.todoList;
    }
}
