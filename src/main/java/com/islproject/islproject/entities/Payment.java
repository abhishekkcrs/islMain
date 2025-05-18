package com.islproject.islproject.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private Long id;
    private String username;
    private String orderId;
    private Long amount;
    private String currency;
    private String paymentStatus;
    private String transactionId;
    private LocalDateTime createdAt;
}
