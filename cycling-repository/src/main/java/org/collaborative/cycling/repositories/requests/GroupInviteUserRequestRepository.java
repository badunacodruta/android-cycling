package org.collaborative.cycling.repositories.requests;

import org.collaborative.cycling.records.requests.GroupInviteUserRequestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GroupInviteUserRequestRepository extends JpaRepository<GroupInviteUserRequestRecord, Long> {

}
