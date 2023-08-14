package com.firisbe.securepay.service;


import com.firisbe.securepay.model.Log;
import com.firisbe.securepay.repository.LogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LogServiceTest {

    @Mock
    private LogRepository logRepository;

    @Test
    public void getAllLog_ShouldReturnAllLogs() {
        // Arrange
        Log log1 = new Log();
        Log log2 = new Log();
        List<Log> logs = List.of(log1, log2);
        when(logRepository.findAll()).thenReturn(logs);

        // Act
        LogService logService = new LogService(logRepository);
        List<Log> allLogs = logService.getAllLog();

        // Assert
        assertEquals(logs, allLogs);
    }
}