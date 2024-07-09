package com.github.lucasbarbosaalves.plannerapi.service.impl;

import com.github.lucasbarbosaalves.plannerapi.entity.participant.ParticipantService;
import com.github.lucasbarbosaalves.plannerapi.entity.trip.Trip;
import com.github.lucasbarbosaalves.plannerapi.entity.trip.dto.TripCreateResponse;
import com.github.lucasbarbosaalves.plannerapi.entity.trip.dto.TripRequestPayload;
import com.github.lucasbarbosaalves.plannerapi.repository.TripRepository;
import com.github.lucasbarbosaalves.plannerapi.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepository repository;
    private final ParticipantService participantService;


    public TripServiceImpl(TripRepository repository, ParticipantService participantService) {
        this.repository = repository;
        this.participantService = participantService;
    }

    @Override
    public ResponseEntity<TripCreateResponse> create(TripRequestPayload payload) {
        Trip newTrip = new Trip(payload);
        this.repository.save(newTrip);
        this.participantService.registerParticipantsToTrip(payload.emails_to_invite(), newTrip.getId());
        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
    }

    @Override
    public ResponseEntity<Trip> get(UUID id) {
        Optional<Trip> trip = this.repository.findById(id);
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Trip> confirm(UUID id) {
        Optional<Trip> trip = this.repository.findById(id);
        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            rawTrip.setIsConfirmed(true);
            this.repository.save(rawTrip);
            this.participantService.triggerConfirmationEmailToParticipant(id);
            return ResponseEntity.ok(rawTrip);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Trip> update(UUID id, TripRequestPayload payload) {
        Optional<Trip> trip = this.repository.findById(id);
        if (trip.isPresent()) {
            Trip rowTrip = trip.get();
            rowTrip.setEndsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            rowTrip.setStartsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            rowTrip.setDestination(payload.destination());
            this.repository.save(rowTrip);

            return ResponseEntity.ok(rowTrip);
        }
        return ResponseEntity.notFound().build();
    }
}
