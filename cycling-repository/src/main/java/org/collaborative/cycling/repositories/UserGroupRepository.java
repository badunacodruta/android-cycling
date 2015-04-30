package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.UserGroupRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserGroupRepository extends JpaRepository<UserGroupRecord, Long> {

}
