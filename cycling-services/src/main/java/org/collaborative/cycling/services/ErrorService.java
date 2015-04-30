package org.collaborative.cycling.services;

import org.collaborative.cycling.records.ErrorRecord;
import org.collaborative.cycling.repositories.ErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ErrorService {

    private final ErrorRepository errorRepository;

    @Autowired
    public ErrorService(ErrorRepository errorRepository) {
        this.errorRepository = errorRepository;
    }

    public void save(Long userId, String errorData) {
        if (errorData == null || errorData.isEmpty()) {
            return;
        }

        ErrorRecord errorRecord = new ErrorRecord(userId, errorData.getBytes(), new Date());
        errorRepository.save(errorRecord);
    }
}
