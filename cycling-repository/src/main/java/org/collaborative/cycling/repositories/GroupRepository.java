package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.GroupRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OrderBy;
import java.util.List;

@Transactional
public interface GroupRepository extends JpaRepository<GroupRecord, Long> {

    GroupRecord findByName(String name);

    List<GroupRecord> findAllByOrderByNameAsc();

    List<GroupRecord> findByNameContainingOrderByNameAsc(String filter);
}
