package com.firisbe.securepay.service;

import com.firisbe.securepay.model.Log;
import com.firisbe.securepay.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    private final LogRepository logRepository;

    public void saveLog(Log log){
        logRepository.save(log);
    }

    public List<Log> getAllLog(){
        return  logRepository.findAll();
    }

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
}
