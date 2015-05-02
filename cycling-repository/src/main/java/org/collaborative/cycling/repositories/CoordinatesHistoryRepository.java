package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.CoordinatesHistoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CoordinatesHistoryRepository extends JpaRepository<CoordinatesHistoryRecord, Long> {

}
