package com.github.lucasbarbosaalves.plannerapi.service;

import com.github.lucasbarbosaalves.plannerapi.dto.ParticipantCreateResponse;
import com.github.lucasbarbosaalves.plannerapi.dto.ParticipantData;
import com.github.lucasbarbosaalves.plannerapi.entity.Participant;
import com.github.lucasbarbosaalves.plannerapi.dto.ParticipantRequestPayload;
import com.github.lucasbarbosaalves.plannerapi.entity.Trip;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {
    void registerParticipantsToTrip(List<String> participantsToInvite, Trip trip);
    void triggerConfirmationEmailToParticipant(UUID tripId);
    ResponseEntity<Participant> confirmParticipant(UUID id, ParticipantRequestPayload payload);
    void triggerInvitationEmailToParticipant(String email);
    ParticipantCreateResponse registerParticipantToTrip(String email, Trip trip);
    ResponseEntity<List<ParticipantData>> getAllParticipantsFromTrip(UUID id);
}
