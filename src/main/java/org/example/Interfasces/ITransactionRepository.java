package org.example.Interfasces;



import org.example.Models.Transaction;

import java.util.List;

/**
 * Интерфейс для работы с репозиторием транзакций.
 * Определяет метод для сохранения транзакций.
 */
public interface ITransactionRepository {

    /**
     * Сохраняет транзакцию в репозитории.
     *
     * @param transaction Транзакция для сохранения.
     */
    void save(Transaction transaction);

    public List<Transaction> getAllTransactions();
}
