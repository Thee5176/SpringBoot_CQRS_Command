package com.thee5176.ledger_command.record.application.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class HealthCheckControllerTest {

    @Test
    void healthCheck_ReturnsHealthyStatus() {
        HealthCheckController controller = new HealthCheckController();
        ResponseEntity<String> response = controller.healthCheck();

        assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        assertEquals("Service is healthy!", response.getBody());
    }
}