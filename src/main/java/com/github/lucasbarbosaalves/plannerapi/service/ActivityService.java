package com.github.lucasbarbosaalves.plannerapi.service;

import com.github.lucasbarbosaalves.plannerapi.dto.ActivityData;
import com.github.lucasbarbosaalves.plannerapi.dto.ActivityRequestPayload;
import com.github.lucasbarbosaalves.plannerapi.dto.ActivityResponse;
import com.github.lucasbarbosaalves.plannerapi.entity.Trip;

import java.util.List;
import java.util.UUID;

public interface ActivityService {

    ActivityResponse registerActivity(ActivityRequestPayload payload, Trip trip);
    List<ActivityData> getAllActivitiesFromId(UUID tripId);
}
