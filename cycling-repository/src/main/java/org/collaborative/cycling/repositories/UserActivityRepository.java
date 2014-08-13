package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.UserActivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityRepository extends JpaRepository<UserActivityRecord, Long> {
}
