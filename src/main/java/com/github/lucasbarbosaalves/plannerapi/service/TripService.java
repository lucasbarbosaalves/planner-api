package com.github.lucasbarbosaalves.plannerapi.service;

import com.github.lucasbarbosaalves.plannerapi.entity.trip.Trip;
import com.github.lucasbarbosaalves.plannerapi.entity.trip.dto.TripCreateResponse;
import com.github.lucasbarbosaalves.plannerapi.entity.trip.dto.TripRequestPayload;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface TripService {
    ResponseEntity<TripCreateResponse> create(TripRequestPayload payload);

    ResponseEntity<Trip> get(UUID id);

    ResponseEntity<Trip> update(UUID id, TripRequestPayload payload);

    ResponseEntity<Trip> confirm(UUID id);

}
