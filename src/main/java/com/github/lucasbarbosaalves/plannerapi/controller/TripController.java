package com.github.lucasbarbosaalves.plannerapi.controller;

import com.github.lucasbarbosaalves.plannerapi.dto.*;
import com.github.lucasbarbosaalves.plannerapi.entity.Participant;
import com.github.lucasbarbosaalves.plannerapi.entity.Trip;
import com.github.lucasbarbosaalves.plannerapi.service.ActivityService;
import com.github.lucasbarbosaalves.plannerapi.service.LinkService;
import com.github.lucasbarbosaalves.plannerapi.service.ParticipantService;
import com.github.lucasbarbosaalves.plannerapi.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/trips")
public class TripController {

   private final TripService tripService;
   private final ParticipantService participantService;
   private final LinkService linkService;
   private final ActivityService activityService;

    public TripController(TripService tripService, ParticipantService participantService, LinkService linkService, ActivityService activityService) {
        this.tripService = tripService;
        this.participantService = participantService;
        this.linkService = linkService;
        this.activityService = activityService;
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

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable(name = "id") UUID id, @RequestBody ParticipantRequestPayload payload) {
        return this.tripService.invite(id, payload);
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getParticipants(@PathVariable(name = "id") UUID id) {
        return this.participantService.getAllParticipantsFromTrip(id);
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkData>> getAllLinks(@PathVariable UUID id) {
        return this.tripService.getAllLinks(id);
    }

    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable UUID id, @RequestBody LinkRequestPayload payload) {
        return this.tripService.registerLink(id, payload);
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityData>> getAllActivities(@PathVariable UUID id) {
        return this.tripService.getAllActivities(id);
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityRequestPayload payload) {
        return this.tripService.registerActivity(id, payload);
    }
}
