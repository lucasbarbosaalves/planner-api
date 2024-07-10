package com.github.lucasbarbosaalves.plannerapi.service.impl;

import com.github.lucasbarbosaalves.plannerapi.dto.LinkData;
import com.github.lucasbarbosaalves.plannerapi.dto.LinkRequestPayload;
import com.github.lucasbarbosaalves.plannerapi.dto.LinkResponse;
import com.github.lucasbarbosaalves.plannerapi.entity.Link;
import com.github.lucasbarbosaalves.plannerapi.entity.Trip;
import com.github.lucasbarbosaalves.plannerapi.repository.LinkRepository;
import com.github.lucasbarbosaalves.plannerapi.service.LinkService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;

    public LinkServiceImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }


    @Override
    public LinkResponse registerLink(LinkRequestPayload payload, Trip trip) {
        Link newLink = new Link(payload.title(), payload.uri(), trip);

        this.linkRepository.save(newLink);

        return new LinkResponse(newLink.getId());
    }

    @Override
    public List<LinkData> getAllLinksFromTrip(UUID tripId) {
        return this.linkRepository.findByTripId(tripId).stream().map(link -> new LinkData(link.getId(), link.getTitle(), link.getUrl())).toList();
    }
}
