package com.example.Vms.service.serviceinterfaces;

import com.example.Vms.entities.Event;
import com.example.Vms.models.EventModel;

public interface EventServiceInterface {
    EventModel save(EventModel eventModel);

    String deleteEvent(int eventId);

    String updateEvent(Event event, int eventId);

    EventModel getEvent(int eventId);
}
