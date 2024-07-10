package com.github.lucasbarbosaalves.plannerapi.dto;

import java.util.UUID;

public record ParticipantData(UUID id, String name, String email, Boolean isConfirmed) {
}
