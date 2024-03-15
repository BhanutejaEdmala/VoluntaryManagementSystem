package com.example.Vms.service;

import com.example.Vms.entities.Event;
import com.example.Vms.models.EventModel;

public interface EventServiceInterface {
    Event save(EventModel eventModel);

    String deleteEvent(int eid);

    Event updateEvent(Event event, int eid);

    Event get(int eid);
}
