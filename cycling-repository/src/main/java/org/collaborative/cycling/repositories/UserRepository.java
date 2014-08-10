package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserRecord, String> {
}
