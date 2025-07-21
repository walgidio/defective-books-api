package com.walgidio.challenge.repository;

import com.walgidio.challenge.domain.Edition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditionRepository extends JpaRepository<Edition, String> {
}
