package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.ActivityRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ActivityRepository extends JpaRepository<ActivityRecord, Long> {

}
