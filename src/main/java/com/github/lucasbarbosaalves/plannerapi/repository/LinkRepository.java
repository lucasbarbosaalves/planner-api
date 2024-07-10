package com.github.lucasbarbosaalves.plannerapi.repository;

import java.util.List;
import java.util.UUID;

import com.github.lucasbarbosaalves.plannerapi.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, UUID> {
    List<Link> findByTripId(UUID tripId);
}