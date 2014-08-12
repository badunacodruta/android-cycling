package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.ActivityRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<ActivityRecord, Long> {

    @Query("select a from ActivityRecord a where a.owner.email = ?1 and a.deleted = false")
    List<ActivityRecord> getActivitiesByPage(String email, Pageable pageable);

    @Query("select count(a) from ActivityRecord a where a.owner.email = ?1 and a.deleted = false")
    int getActivitiesCount(String email);
}
