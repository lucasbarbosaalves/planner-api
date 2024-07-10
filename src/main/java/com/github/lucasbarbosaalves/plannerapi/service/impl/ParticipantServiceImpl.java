package com.github.lucasbarbosaalves.plannerapi.service.impl;

import com.github.lucasbarbosaalves.plannerapi.dto.ParticipantCreateResponse;
import com.github.lucasbarbosaalves.plannerapi.dto.ParticipantData;
import com.github.lucasbarbosaalves.plannerapi.entity.Participant;
import com.github.lucasbarbosaalves.plannerapi.dto.ParticipantRequestPayload;
import com.github.lucasbarbosaalves.plannerapi.entity.Trip;
import com.github.lucasbarbosaalves.plannerapi.repository.ParticipantRepository;
import com.github.lucasbarbosaalves.plannerapi.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository repository;

    public ParticipantServiceImpl(ParticipantRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registerParticipantsToTrip(List<String> participantsToInvite, Trip trip) {
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();
        this.repository.saveAll(participants);
        System.out.println(participants.get(0).getId());

    }

    @Override
    public void triggerConfirmationEmailToParticipant(UUID tripId) {
    }

    @Override
    public ResponseEntity<Participant> confirmParticipant(UUID id, ParticipantRequestPayload payload) {
        Optional<Participant> participant = this.repository.findById(id);
        if (participant.isPresent()) {
            Participant updatedParticipant = participant.get();
            updatedParticipant.setIsConfirmed(true);
            updatedParticipant.setName(payload.name());

            this.repository.save(updatedParticipant);
            System.out.println(updatedParticipant.getId());

            return ResponseEntity.ok(updatedParticipant);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public void triggerInvitationEmailToParticipant(String email) {
    }

    @Override
    public ParticipantCreateResponse registerParticipantToTrip(String email, Trip trip) {
        Participant newParticipant = new Participant(email, trip);
        this.repository.save(newParticipant);
        return new ParticipantCreateResponse(newParticipant.getId());
    }

    @Override
    public ResponseEntity<List<ParticipantData>> getAllParticipantsFromTrip(UUID id) {
        List<ParticipantData> participantList = this.repository.findByTripId(id).stream().map(participant -> new ParticipantData(participant.getId(), participant.getEmail(), participant.getName(), participant.getIsConfirmed())).toList();
        return ResponseEntity.ok(participantList);
    }
}
