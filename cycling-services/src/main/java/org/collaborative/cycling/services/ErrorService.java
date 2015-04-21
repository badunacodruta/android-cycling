package org.collaborative.cycling.services;

import org.collaborative.cycling.records.ErrorRecord;
import org.collaborative.cycling.repositories.ErrorRepository;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ErrorService {

  private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ErrorService.class);

  private ErrorRepository errorRepository;

  public ErrorService(ErrorRepository errorRepository) {
    this.errorRepository = errorRepository;
  }

  public void save(String user, String errorData) {
    errorRepository.save(new ErrorRecord(user, errorData.getBytes(), new Date()));
  }

}
