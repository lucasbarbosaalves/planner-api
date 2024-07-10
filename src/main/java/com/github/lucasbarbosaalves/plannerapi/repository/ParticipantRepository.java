package com.github.lucasbarbosaalves.plannerapi.repository;

import com.github.lucasbarbosaalves.plannerapi.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    List<Participant> findByTripId(UUID tripId);
}
