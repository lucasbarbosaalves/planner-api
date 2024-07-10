package com.github.lucasbarbosaalves.plannerapi.repository;

import com.github.lucasbarbosaalves.plannerapi.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
}
