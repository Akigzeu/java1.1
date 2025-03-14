package org.example.repositories;

import lombok.Getter;
import org.example.Interfasces.ITransactionRepository;
import org.example.Models.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация репозитория для работы с транзакциями.
 * Хранит данные в памяти (в списке) вместо взаимодействия с базой данных.
 */
@Getter
public class InMemoryTransactionRepository implements ITransactionRepository {

    /**
     * Список для хранения транзакций.
     */
    private final List<Transaction> transactions = new ArrayList<>();

    /**
     * Сохраняет транзакцию в репозитории.
     *
     * @param transaction Транзакция для сохранения.
     */
    @Override
    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Возвращает список всех транзакций.
     *
     * @return Список транзакций.
     */
    @Override
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
}