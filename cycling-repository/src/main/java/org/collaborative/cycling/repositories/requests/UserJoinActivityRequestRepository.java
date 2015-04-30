package org.collaborative.cycling.repositories.requests;

import org.collaborative.cycling.records.requests.UserJoinActivityRequestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserJoinActivityRequestRepository extends JpaRepository<UserJoinActivityRequestRecord, Long> {

}
