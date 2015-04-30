package org.collaborative.cycling.repositories.requests;

import org.collaborative.cycling.records.requests.UserJoinGroupRequestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserJoinGroupRequestRepository extends JpaRepository<UserJoinGroupRequestRecord, Long> {

}
