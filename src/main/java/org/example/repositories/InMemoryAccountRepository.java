package org.example.repositories;

import lombok.Getter;
import org.example.Interfasces.IAccountRepository;
import org.example.Models.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Реализация репозитория для работы со счетами.
 * Хранит данные в памяти (в списке) вместо взаимодействия с базой данных.
 */
@Getter
public class InMemoryAccountRepository implements IAccountRepository {

    /**
     * Список для хранения счетов.
     */
    private final List<Account> accounts = new ArrayList<>();

    /**
     * Получает счет по номеру счета и PIN-коду.
     *
     * @param accountNumber Номер счета.
     * @param pin           PIN-код для доступа к счету.
     * @return Объект {@link Optional}, содержащий счет, если он найден, или пустой {@link Optional}, если счет не найден.
     */
    @Override
    public Optional<Account> getAccount(String accountNumber, String pin) {
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber) && account.getPin().equals(pin))
                .findFirst();
    }

    /**
     * Сохраняет или обновляет счет в репозитории.
     * Если счет с таким номером уже существует, он обновляется. В противном случае добавляется новый счет.
     *
     * @param account Счет для сохранения.
     */
    @Override
    public void save(Account account) {
        // Проверяем, существует ли счет с таким номером
        Optional<Account> existingAccount = accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(account.getAccountNumber()))
                .findFirst();

        if (existingAccount.isPresent()) {
            // Обновляем существующий счет
            Account acc = existingAccount.get();
            acc.setBalance(account.getBalance());
        } else {
            // Добавляем новый счет
            accounts.add(account);
        }
    }
}
