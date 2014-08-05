package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<UserRecord, String> {
}
