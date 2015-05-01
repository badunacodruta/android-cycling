package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.UserActivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserActivityRepository extends JpaRepository<UserActivityRecord, Long> {

    UserActivityRecord findByUserIdAndActivityId(Long userId, Long activityId);

}
