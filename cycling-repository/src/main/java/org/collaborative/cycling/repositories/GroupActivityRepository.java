package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.GroupActivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GroupActivityRepository extends JpaRepository<GroupActivityRecord, Long> {

    GroupActivityRecord findByGroupIdAndActivityId(Long groupId, long activityId);
}
