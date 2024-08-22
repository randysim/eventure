package com.eventure.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Day extends JpaRepository<Day, Long> {
}
