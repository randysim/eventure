package com.eventure.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "todolist")
public class TodoList {
    @Id
    @SequenceGenerator(
            name = "todolist_sequence",
            sequenceName = "todolist_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "todolist_sequence"
    )
    private Long id;

    @OneToOne(mappedBy = "todoList")
    @JsonBackReference
    private Plan plan;

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Task> tasks;

    public TodoList() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        task.setTodoList(this);
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setTodoList(null);
    }
}
