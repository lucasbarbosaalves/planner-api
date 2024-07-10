package com.github.lucasbarbosaalves.plannerapi.service.impl;

import com.github.lucasbarbosaalves.plannerapi.dto.*;
import com.github.lucasbarbosaalves.plannerapi.entity.Participant;
import com.github.lucasbarbosaalves.plannerapi.service.ActivityService;
import com.github.lucasbarbosaalves.plannerapi.service.LinkService;
import com.github.lucasbarbosaalves.plannerapi.service.ParticipantService;
import com.github.lucasbarbosaalves.plannerapi.entity.Trip;
import com.github.lucasbarbosaalves.plannerapi.repository.TripRepository;
import com.github.lucasbarbosaalves.plannerapi.service.TripService;
import jakarta.servlet.http.Part;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepository repository;
    private final ParticipantService participantService;
    private final LinkService linkService;
    private final ActivityService activityService;


    public TripServiceImpl(TripRepository repository, ParticipantService participantService, LinkService linkService, ActivityService activityService) {
        this.repository = repository;
        this.participantService = participantService;
        this.linkService = linkService;
        this.activityService = activityService;
    }

    @Override
    public ResponseEntity<TripCreateResponse> create(TripRequestPayload payload) {
        Trip newTrip = new Trip(payload);
        this.repository.save(newTrip);
        this.participantService.registerParticipantsToTrip(payload.emails_to_invite(), newTrip);
        URI uri = URI.create("/trips/" + newTrip.getId());
        return ResponseEntity.created(uri).body(new TripCreateResponse(newTrip.getId()));
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

    @Override
    public ResponseEntity<ParticipantCreateResponse> invite(UUID id, ParticipantRequestPayload participant) {
            Optional<Trip> trip = this.repository.findById(id);
            if (trip.isPresent()) {
                Trip rawTrip = trip.get();
                ParticipantCreateResponse response = this.participantService.registerParticipantToTrip(participant.email(), rawTrip);
                if (rawTrip.getIsConfirmed()) this.participantService.triggerInvitationEmailToParticipant(participant.email());

                return ResponseEntity.ok(response);
            }
            return ResponseEntity.notFound().build();
        }

        @Override
    public ResponseEntity<LinkResponse> registerLink(UUID id, LinkRequestPayload payload) {
        Optional<Trip> trip = this.repository.findById(id);
        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            LinkResponse response = this.linkService.registerLink(payload, rawTrip);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<LinkData>> getAllLinks(UUID id) {
        List<LinkData> linkDataList = this.linkService.getAllLinksFromTrip(id);
        return ResponseEntity.ok(linkDataList);
    }

    @Override
    public ResponseEntity<List<ActivityData>> getAllActivities(UUID id) {
        List<ActivityData> activityDataList = this.activityService.getAllActivitiesFromId(id);
        return ResponseEntity.ok(activityDataList);
    }

    @Override
    public ResponseEntity<ActivityResponse> registerActivity(UUID id, ActivityRequestPayload payload) {
        Optional<Trip> trip = this.repository.findById(id);

        if(trip.isPresent()){
            Trip rawTrip = trip.get();

            ActivityResponse activityResponse = this.activityService.registerActivity(payload, rawTrip);

            return ResponseEntity.ok(activityResponse);
        }
        return ResponseEntity.notFound().build();
    }
}
