package com.bank.domain;
import com.bank.util.BankExceptions;
import java.math.BigDecimal;
public class SavingsAccount extends Account implements InterestBearing {
    private final BigDecimal monthlyRate;
    public SavingsAccount(String accountNo, Customer owner, BigDecimal openingBalance, BigDecimal monthlyRate) {
        super(accountNo, owner, openingBalance); this.monthlyRate = monthlyRate;
    }
    @Override public void withdraw(BigDecimal amount, String note) {
        throw new BankExceptions.InvalidOperation("Withdrawals are not allowed from SavingsAccount");
    }
    @Override public BigDecimal calculateInterest() { return getBalance().multiply(monthlyRate); }
}
