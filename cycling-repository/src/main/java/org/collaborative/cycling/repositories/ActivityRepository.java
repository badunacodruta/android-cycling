package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.ActivityRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ActivityRepository extends JpaRepository<ActivityRecord, Long> {

    @Query("select a from ActivityRecord a where a.owner.email = ?1 and a.deleted = false")
    List<ActivityRecord> getActivitiesByPage(String email, Pageable pageable);

    @Query("select count(a) from ActivityRecord a where a.owner.email = ?1 and a.deleted = false")
    int getActivitiesCount(String email);

    @Query("select a from ActivityRecord a where not a.owner.email = ?1 and a.deleted = false and a.name like ?2 order by a.name")
    List<ActivityRecord> getActivitiesByName(String email, String name);
}
