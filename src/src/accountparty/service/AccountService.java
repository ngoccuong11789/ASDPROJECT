package accountparty.service;


import accountparty.account.Account;
import accountparty.notification.Observer;
import accountparty.notification.TransactionObservable;
import accountparty.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;

public abstract class AccountService implements AccountTransactionService {

    private final AccountRepository accountRepository;
    private final TransactionObservable transactionObservable;



    protected AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.transactionObservable = new TransactionObservable();
    }


    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        account.deposit(amount);
        accountRepository.update(account);
        transactionObservable.notifyObservers(account, "deposit", amount);
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        account.withdraw(amount);
        accountRepository.update(account);
        transactionObservable.notifyObservers(account, "withdraw", amount);
    }

    public void transferFunds(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description) {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);
        fromAccount.transferFunds(toAccount, amount);
        accountRepository.update(fromAccount);
        accountRepository.update(toAccount);
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public TransactionObservable getTransactionObservable() {
        return transactionObservable;
    }

    @Override
    public void addTransactionObserver(Observer observer) {
        transactionObservable.addObserver(observer);
    }


    public void addInterestToAllAccounts() {
        List<Account> accounts = accountRepository.getAllAccounts();
        for (Account account : accounts) {
            account.addInterest();
            accountRepository.update(account);
        }
    }

}
