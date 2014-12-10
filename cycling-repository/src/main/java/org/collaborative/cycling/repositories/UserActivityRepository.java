package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.UserActivityRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserActivityRepository extends JpaRepository<UserActivityRecord, Long> {

    @Query("select ua from UserActivityRecord ua where ua.user.email = ?1 and ua.deleted = false")
    List<UserActivityRecord> getUserActivitiesByPage(String email, Pageable pageable);

    @Query("select ua from UserActivityRecord ua where ua.user.email = ?1 and ua.activity.deleted = false order by ua.activity.name")
    List<UserActivityRecord> getUserActivities(String email);

}
