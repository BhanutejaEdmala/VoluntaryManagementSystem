package com.example.Vms.service.serviceinterfaces;

import com.example.Vms.entities.Event;
import com.example.Vms.models.EventModel;

public interface EventServiceInterface {
    EventModel save(EventModel eventModel);

    String deleteEvent(int eid);

    String updateEvent(Event event, int eid);

    EventModel get(int eid);
}
