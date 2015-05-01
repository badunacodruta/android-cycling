package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.UserMessageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserMessageRepository extends JpaRepository<UserMessageRecord, Long> {

}
