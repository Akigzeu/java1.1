package org.example.services;


import lombok.AllArgsConstructor;
import org.example.Interfasces.IAccountRepository;
import org.example.Interfasces.ITransactionRepository;
import org.example.Models.Account;
import org.example.Models.Transaction;

import java.util.List;

/**
 * Сервис для работы с банковскими операциями.
 * Обеспечивает создание счетов, пополнение, снятие средств, проверку баланса и просмотр транзакций.
 */
@AllArgsConstructor
public class BankService {

    private final IAccountRepository accountRepository;
    private final ITransactionRepository transactionRepository;

    /**
     * Создает новый счет.
     *
     * @param accountNumber Номер счета.
     * @param pin           PIN-код для доступа к счету.
     * @throws IllegalStateException Если счет с таким номером уже существует.
     */
    public void createAccount(String accountNumber, String pin) {
        if (accountRepository.getAccount(accountNumber, pin).isPresent()) {
            throw new IllegalStateException("Номер счета уже существует.");
        }

        Account account = new Account(accountNumber, pin);
        accountRepository.save(account);
    }

    /**
     * Пополняет счет на указанную сумму.
     *
     * @param accountNumber Номер счета.
     * @param pin           PIN-код для доступа к счету.
     * @param amount        Сумма для пополнения.
     * @throws IllegalArgumentException Если сумма отрицательная.
     * @throws IllegalStateException    Если счет не найден.
     */
        public void deposit(String accountNumber, String pin, Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма для пополнения не может быть отрицательной.");
        }

        Account account = accountRepository.getAccount(accountNumber, pin)
                .orElseThrow(() -> new IllegalStateException("Некорректные данные для доступа."));

        account.deposit(amount);
        accountRepository.save(account);
        transactionRepository.save(new Transaction(account.getId(), amount, "Deposit"));
    }

    /**
     * Снимает средства со счета.
     *
     * @param accountNumber Номер счета.
     * @param pin           PIN-код для доступа к счету.
     * @param amount        Сумма для снятия.
     * @throws IllegalArgumentException Если сумма отрицательная.
     * @throws IllegalStateException    Если счет не найден или недостаточно средств.
     */
    public void withdraw(String accountNumber, String pin, Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма для снятия не может быть отрицательной.");
        }

        Account account = accountRepository.getAccount(accountNumber, pin)
                .orElseThrow(() -> new IllegalStateException("Некорректные данные для доступа."));

        account.withdraw(amount);
        accountRepository.save(account);
        transactionRepository.save(new Transaction(account.getId(), amount, "Withdrawal"));
    }

    /**
     * Возвращает текущий баланс счета.
     *
     * @param accountNumber Номер счета.
     * @param pin           PIN-код для доступа к счету.
     * @return Текущий баланс счета.
     * @throws IllegalStateException Если счет не найден.
     */
    public double getBalance(String accountNumber, String pin) {
        Account account = accountRepository.getAccount(accountNumber, pin)
                .orElseThrow(() -> new IllegalStateException("Некорректные данные для доступа."));
        return account.getBalance();
    }

    /**
     * Возвращает список всех транзакций для указанного счета.
     *
     * @param accountNumber Номер счета.
     * @param pin           PIN-код для доступа к счету.
     * @return Список транзакций.
     * @throws IllegalStateException Если счет не найден.
     */
    public List<Transaction> getTransactions(String accountNumber, String pin) {
        Account account = accountRepository.getAccount(accountNumber, pin)
                .orElseThrow(() -> new IllegalStateException("Некорректные данные для доступа."));

        return transactionRepository.getAllTransactions().stream()
                .filter(t -> t.getAccountId() == account.getId())
                .toList();
    }
}
