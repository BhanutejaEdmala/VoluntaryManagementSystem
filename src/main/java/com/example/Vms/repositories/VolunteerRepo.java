package com.example.Vms.repositories;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerRepo extends JpaRepository<Volunteer,Integer> {
    @Query("SELECT DISTINCT e FROM Event e JOIN e.organisations o JOIN e.volunteerList v " +
            "WHERE v.vid = :vid AND o.oid = :oid")
    List<Event> findEventsByVolunteerAndOrganisation(@Param("vid") int vid, @Param("oid") int oid);

}
