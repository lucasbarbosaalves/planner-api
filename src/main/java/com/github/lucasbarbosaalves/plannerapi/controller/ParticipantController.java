package com.github.lucasbarbosaalves.plannerapi.controller;

import com.github.lucasbarbosaalves.plannerapi.dto.ParticipantCreateResponse;
import com.github.lucasbarbosaalves.plannerapi.entity.Participant;
import com.github.lucasbarbosaalves.plannerapi.dto.ParticipantRequestPayload;
import com.github.lucasbarbosaalves.plannerapi.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable(name = "id") UUID id, @RequestBody ParticipantRequestPayload payload) {
        return this.participantService.confirmParticipant(id, payload);
    }

}
