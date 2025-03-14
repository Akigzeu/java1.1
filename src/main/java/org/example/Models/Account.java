package org.example.Models;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

/**
 * Класс, представляющий банковский счет.
 * Содержит информацию о номере счета, PIN-коде, балансе и методах для работы с балансом.
 */
@Data
public class Account {

    /**
     * Уникальный идентификатор счета.
     */
    private Integer id;

    /**
     * Номер счета.
     */
    @NonNull
    private String accountNumber;

    /**
     * PIN-код для доступа к счету.
     */
    @NonNull
    private String pin;

    /**
     * Текущий баланс счета.
     */
    private Double balance;

    /**
     * Конструктор для создания нового счета с указанием номера счета и PIN-кода.
     *
     * @param accountNumber Номер счета.
     * @param pin           PIN-код для доступа к счету.
     */
    public Account(@NonNull String accountNumber, @NonNull String pin) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = 0.0; // Баланс по умолчанию
    }

    /**
     * Конструктор для создания нового счета с указанием всех полей.
     *
     * @param id            Уникальный идентификатор счета.
     * @param accountNumber Номер счета.
     * @param pin           PIN-код для доступа к счету.
     * @param balance       Текущий баланс счета.
     */
    public Account(Integer id, @NonNull String accountNumber, @NonNull String pin, Double balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    /**
     * Метод для пополнения счета.
     *
     * @param amount Сумма для пополнения.
     * @throws IllegalArgumentException Если сумма отрицательная.
     */
    public void deposit(Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма для пополнения не может быть отрицательной.");
        }
        this.balance += amount;
    }

    /**
     * Метод для снятия средств со счета.
     *
     * @param amount Сумма для снятия.
     * @throws IllegalArgumentException Если сумма отрицательная.
     * @throws IllegalStateException    Если на счете недостаточно средств.
     */
    public void withdraw(Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма для снятия не может быть отрицательной.");
        }
        if (this.balance < amount) {
            throw new IllegalStateException("Недостаточно средств на счете.");
        }
        this.balance -= amount;
    }
}
