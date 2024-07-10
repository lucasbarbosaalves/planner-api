package com.github.lucasbarbosaalves.plannerapi.service;

import com.github.lucasbarbosaalves.plannerapi.dto.LinkData;
import com.github.lucasbarbosaalves.plannerapi.dto.LinkRequestPayload;
import com.github.lucasbarbosaalves.plannerapi.dto.LinkResponse;
import com.github.lucasbarbosaalves.plannerapi.entity.Trip;

import java.util.List;
import java.util.UUID;

public interface LinkService {

    LinkResponse registerLink(LinkRequestPayload payload, Trip trip);

    List<LinkData> getAllLinksFromTrip(UUID tripId);
}
