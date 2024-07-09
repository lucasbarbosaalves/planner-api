package com.github.lucasbarbosaalves.plannerapi.controller;

import com.github.lucasbarbosaalves.plannerapi.entity.participant.ParticipantService;
import com.github.lucasbarbosaalves.plannerapi.entity.trip.Trip;
import com.github.lucasbarbosaalves.plannerapi.entity.trip.dto.TripCreateResponse;
import com.github.lucasbarbosaalves.plannerapi.entity.trip.dto.TripRequestPayload;
import com.github.lucasbarbosaalves.plannerapi.repository.TripRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/trips")
public class TripController {

    private final ParticipantService participantService;
    private final TripRepository repository;

    public TripController(ParticipantService participantService, TripRepository repository) {
        this.participantService = participantService;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) {
        Trip newTrip = new Trip(payload);
        this.repository.save(newTrip);
        this.participantService.registerParticipantsToTrip(payload.emails_to_invite(), newTrip.getId());
        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable(name = "id") UUID id) {
        Optional<Trip> trip = this.repository.findById(id);
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
