package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.ActivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<ActivityRecord, Long> {
}
