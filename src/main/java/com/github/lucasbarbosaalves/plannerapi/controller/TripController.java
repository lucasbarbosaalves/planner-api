package com.github.lucasbarbosaalves.plannerapi.controller;

import com.github.lucasbarbosaalves.plannerapi.entity.participant.ParticipantService;
import com.github.lucasbarbosaalves.plannerapi.entity.trip.Trip;
import com.github.lucasbarbosaalves.plannerapi.entity.trip.dto.TripCreateResponse;
import com.github.lucasbarbosaalves.plannerapi.entity.trip.dto.TripRequestPayload;
import com.github.lucasbarbosaalves.plannerapi.repository.TripRepository;
import com.github.lucasbarbosaalves.plannerapi.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/trips")
public class TripController {

   private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) {
        return tripService.create(payload);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Trip> getTripDetails(@PathVariable(name = "id") UUID id) {
        return tripService.get(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable(name = "id") UUID id, @RequestBody TripRequestPayload payload) {
        return tripService.update(id, payload);
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable(name = "id") UUID id) {
        return tripService.confirm(id);
    }
}
