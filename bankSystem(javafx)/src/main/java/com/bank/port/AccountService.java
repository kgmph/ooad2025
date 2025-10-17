package com.bank.port;
import com.bank.domain.Account; import com.bank.domain.Customer; import com.bank.service.Results;
import java.math.BigDecimal; import java.util.Collection; import java.util.List;
public interface AccountService {
    void addAccount(Account account);
    Account findAccount(String accountNo);
    List<Account> getAccountsFor(Customer c);
    Results.DepositResult deposit(String accountNo, BigDecimal amount, String note);
    Results.WithdrawResult withdraw(String accountNo, BigDecimal amount, String note);
    Results.TransferResult transfer(String fromAcc, String toAcc, BigDecimal amount, String note);
    void applyMonthlyInterest();
    Collection<Account> getAllAccounts();
}
