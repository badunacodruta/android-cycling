package org.collaborative.cycling.repositories.requests;

import org.collaborative.cycling.records.requests.GroupInviteUserToActivityRequestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GroupInviteUserToActivityRequestRepository extends JpaRepository<GroupInviteUserToActivityRequestRecord, Long> {

}
