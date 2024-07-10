package com.github.lucasbarbosaalves.plannerapi.service;

import com.github.lucasbarbosaalves.plannerapi.dto.*;
import com.github.lucasbarbosaalves.plannerapi.entity.Trip;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TripService {
    ResponseEntity<TripCreateResponse> create(TripRequestPayload payload);

    ResponseEntity<Trip> get(UUID id);

    ResponseEntity<Trip> update(UUID id, TripRequestPayload payload);

    ResponseEntity<Trip> confirm(UUID id);

    ResponseEntity<ParticipantCreateResponse> invite(UUID id, ParticipantRequestPayload participant);

    ResponseEntity<LinkResponse> registerLink(UUID id, LinkRequestPayload payload);

    ResponseEntity<List<LinkData>> getAllLinks(UUID id);

    ResponseEntity<List<ActivityData>> getAllActivities(UUID id);

    ResponseEntity<ActivityResponse> registerActivity(UUID id, ActivityRequestPayload payload);

}
