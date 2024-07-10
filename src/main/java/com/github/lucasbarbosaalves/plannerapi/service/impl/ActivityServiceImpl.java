package com.github.lucasbarbosaalves.plannerapi.service.impl;

import com.github.lucasbarbosaalves.plannerapi.dto.ActivityData;
import com.github.lucasbarbosaalves.plannerapi.dto.ActivityRequestPayload;
import com.github.lucasbarbosaalves.plannerapi.dto.ActivityResponse;
import com.github.lucasbarbosaalves.plannerapi.entity.Activity;
import com.github.lucasbarbosaalves.plannerapi.entity.Trip;
import com.github.lucasbarbosaalves.plannerapi.repository.ActivityRepository;
import com.github.lucasbarbosaalves.plannerapi.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }


    @Override
    public ActivityResponse registerActivity(ActivityRequestPayload payload, Trip trip) {
        Activity newActivity = new Activity(payload.title(), payload.occurs_at(), trip);
        this.activityRepository.save(newActivity);

        return new ActivityResponse(newActivity.getId());
    }

    @Override
    public List<ActivityData> getAllActivitiesFromId(UUID tripId) {
        return this.activityRepository.findByTripId(tripId).stream().map(activity -> new ActivityData(activity.getId(), activity.getTitle(), activity.getOccursAt())).toList();
    }
}
