package ec.tcs.banktransactions.application.dto;

import java.time.LocalDateTime;

public record ErrorDTO(LocalDateTime timestamp, String message, String details) {}