package org.collaborative.cycling.repositories;

import org.collaborative.cycling.records.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserRecord, Long> {

    UserRecord findByEmail(String email);
}
