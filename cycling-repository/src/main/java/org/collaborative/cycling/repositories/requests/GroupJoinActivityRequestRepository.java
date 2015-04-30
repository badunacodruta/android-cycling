package org.collaborative.cycling.repositories.requests;

import org.collaborative.cycling.records.requests.GroupJoinActivityRequestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GroupJoinActivityRequestRepository extends JpaRepository<GroupJoinActivityRequestRecord, Long> {

}
