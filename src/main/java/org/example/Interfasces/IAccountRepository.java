package org.example.Interfasces;


import org.example.Models.Account;

import java.util.Optional;

/**
 * Интерфейс для работы с репозиторием счетов.
 * Определяет методы для получения и сохранения счетов.
 */
public interface IAccountRepository {

    /**
     * Получает счет по номеру счета и PIN-коду.
     *
     * @param accountNumber Номер счета.
     * @param pin           PIN-код для доступа к счету.
     * @return Объект {@link Optional}, содержащий счет, если он найден, или пустой {@link Optional}, если счет не найден.
     */
    Optional<Account> getAccount(String accountNumber, String pin);

    /**
     * Сохраняет или обновляет счет в репозитории.
     *
     * @param account Счет для сохранения.
     */
    void save(Account account);
}
