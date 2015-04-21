package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.ErrorRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ErrorRepository extends JpaRepository<ErrorRecord, Long> {

}
