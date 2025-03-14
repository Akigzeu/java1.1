package org.example;

import org.example.Interfasces.IAccountRepository;
import org.example.Interfasces.ITransactionRepository;
import org.example.Models.Transaction;
import org.example.repositories.InMemoryAccountRepository;
import org.example.repositories.InMemoryTransactionRepository;
import org.example.services.BankService;

import java.util.List;
import java.util.Scanner;

public class Program {

    private static BankService bankService;

    public static void main(String[] args) {
        // Инициализация репозиториев и сервиса
        IAccountRepository accountRepository = new InMemoryAccountRepository();
        ITransactionRepository transactionRepository = new InMemoryTransactionRepository();
        bankService = new BankService(accountRepository, transactionRepository);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите режим: 1 - Пользователь, 2 - Администратор, 0 - Выход");
            String option = scanner.nextLine();
            if (option.equals("0")) break;

            switch (option) {
                case "1" -> {
                    System.out.print("Введите номер счета: ");
                    String accountNumber = scanner.nextLine();
                    System.out.print("Введите ПИН-код: ");
                    String pin = scanner.nextLine();
                    userInterface(accountNumber, pin);
                }
                case "2" -> {
                    System.out.print("Введите системный пароль: ");
                    String password = scanner.nextLine();
                    if (!password.equals("Sd1357cv2")) {
                        System.out.println("Неверный пароль. Завершение работы.");
                        return;
                    } else {
                        System.out.println("Админский режим. Пока нет функционала.");
                    }
                }
                default -> System.out.println("Некорректный выбор.");
            }
        }
    }

    /**
     * Интерфейс для работы пользователя.
     *
     * @param accountNumber Номер счета.
     * @param pin           ПИН-код.
     */
    private static void userInterface(String accountNumber, String pin) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберите операцию:");
            System.out.println("1. Просмотр баланса");
            System.out.println("2. Снятие средств");
            System.out.println("3. Пополнение счета");
            System.out.println("4. История операций");
            System.out.println("5. Создать счет");
            System.out.println("0. Выход");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> {
                        double balance = bankService.getBalance(accountNumber, pin);
                        System.out.println("Баланс: " + balance);
                    }
                    case "2" -> {
                        System.out.print("Введите сумму для снятия: ");
                        if (scanner.hasNextBigDecimal()) {
                            double withdrawAmount = scanner.nextDouble();
                            scanner.nextLine(); // Очистка буфера
                            bankService.withdraw(accountNumber, pin, withdrawAmount);
                            System.out.println("Средства успешно сняты.");
                        } else {
                            System.out.println("Некорректный ввод суммы.");
                            scanner.nextLine(); // Очистка буфера
                        }
                    }
                    case "3" -> {
                        System.out.print("Введите сумму для пополнения: ");
                        if (scanner.hasNextDouble()) {
                            double depositAmount = scanner.nextDouble();
                            scanner.nextLine(); // Очистка буфера
                            bankService.deposit(accountNumber, pin, depositAmount);
                            System.out.println("Счет успешно пополнен.");
                        } else {
                            System.out.println("Некорректный ввод суммы.");
                            scanner.nextLine(); // Очистка буфера
                        }
                    }
                    case "4" -> {
                        List<Transaction> transactions = bankService.getTransactions(accountNumber, pin);
                        System.out.println("История операций:");
                        transactions.forEach(t -> System.out.println(t));
                    }
                    case "5" -> {
                        System.out.print("Введите номер нового счета: ");
                        String newAccountNumber = scanner.nextLine();
                        System.out.print("Введите ПИН-код для нового счета: ");
                        String newPin = scanner.nextLine();
                        bankService.createAccount(newAccountNumber, newPin);
                        System.out.println("Счет успешно создан!");
                    }
                    case "0" -> {
                        return;
                    }
                    default -> System.out.println("Некорректный выбор.");
                }
            } catch (IllegalStateException | IllegalArgumentException ex) {
                System.out.println("Ошибка: " + ex.getMessage());
            }
        }
    }
}