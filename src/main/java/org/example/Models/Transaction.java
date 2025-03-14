package org.example.Models;

import lombok.Data;


import java.time.LocalDateTime;

/**
 * Класс, представляющий транзакцию.
 * Используется для хранения информации о транзакциях, таких как депозиты и снятия средств.
 */
@Data
public class Transaction {

    /**
     * Уникальный идентификатор транзакции.
     */
    private Integer id;

    /**
     * Идентификатор счета, связанного с транзакцией.
     */
    private Integer accountId;

    /**
     * Временная метка транзакции.
     */
    private LocalDateTime timestamp;

    /**
     * Сумма транзакции.
     */
    private Double amount;

    /**
     * Тип транзакции. Может быть "Deposit" (депозит) или "Withdrawal" (снятие).
     */
    private String type;

    /**
     * Конструктор для создания новой транзакции.
     *
     * @param accountId Идентификатор счета, связанного с транзакцией.
     * @param amount    Сумма транзакции.
     * @param type      Тип транзакции ("Deposit" или "Withdrawal").
     */
    public Transaction(Integer accountId, Double amount, String type) {
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }
}