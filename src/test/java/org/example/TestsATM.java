package org.example;

import org.example.Interfasces.IAccountRepository;
import org.example.Interfasces.ITransactionRepository;
import org.example.Models.Account;
import org.example.Models.Transaction;
import org.example.services.BankService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class TestsATM {
    @Test
    void withdraw_SufficientFunds_UpdatesBalanceCorrectly() {

        IAccountRepository mockAccountRepository = mock(IAccountRepository.class);
        ITransactionRepository mockTransactionRepository = mock(ITransactionRepository.class);

        BankService bankService = new BankService(mockAccountRepository, mockTransactionRepository);

        Account account = new Account("12345", "1111");
        account.setId(1);
        account.setBalance(Double.valueOf("900.00"));

        when(mockAccountRepository.getAccount("12345", "1111")).thenReturn(java.util.Optional.of(account));


        bankService.withdraw("12345", "1111", Double.valueOf("50.00"));

        // Assert
        verify(mockAccountRepository, times(1)).save(argThat(a -> a.getBalance().compareTo(Double.valueOf("50.00")) == 0));
        verify(mockTransactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void withdraw_InsufficientFunds_ThrowsException() {

        IAccountRepository mockAccountRepository = mock(IAccountRepository.class);
        ITransactionRepository mockTransactionRepository = mock(ITransactionRepository.class);

        BankService bankService = new BankService(mockAccountRepository, mockTransactionRepository);

        Account account = new Account("12345", "1111");
        account.setId(1);
        account.setBalance(Double.valueOf("50.00"));

        when(mockAccountRepository.getAccount("12345", "1111")).thenReturn(java.util.Optional.of(account));


        assertThrows(IllegalStateException.class, () -> bankService.withdraw("12345", "1111", Double.valueOf("100.00")));

        verify(mockAccountRepository, never()).save(any(Account.class));
        verify(mockTransactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void deposit_UpdatesBalanceCorrectly() {

        IAccountRepository mockAccountRepository = mock(IAccountRepository.class);
        ITransactionRepository mockTransactionRepository = mock(ITransactionRepository.class);

        BankService bankService = new BankService(mockAccountRepository, mockTransactionRepository);

        Account account = new Account("12345", "1111");
        account.setId(1);
        account.setBalance(Double.valueOf("50.00"));

        when(mockAccountRepository.getAccount("12345", "1111")).thenReturn(java.util.Optional.of(account));


        bankService.deposit("12345", "1111", Double.valueOf("75.00"));


        verify(mockAccountRepository, times(1)).save(argThat(a -> a.getBalance().compareTo(Double.valueOf("125.00")) == 0));
        verify(mockTransactionRepository, times(1)).save(any(Transaction.class));
    }
}
