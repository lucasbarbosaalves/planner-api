package com.github.lucasbarbosaalves.plannerapi.repository;

import com.github.lucasbarbosaalves.plannerapi.entity.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
}
