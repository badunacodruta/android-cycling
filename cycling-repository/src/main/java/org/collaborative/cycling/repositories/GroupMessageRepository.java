package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.GroupMessageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GroupMessageRepository extends JpaRepository<GroupMessageRecord, Long> {

}
