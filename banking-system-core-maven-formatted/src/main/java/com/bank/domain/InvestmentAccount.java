package com.bank.domain;
import com.bank.util.BankExceptions;
import java.math.BigDecimal;
public class InvestmentAccount extends Account implements InterestBearing {
    private final BigDecimal monthlyRate;
    public static final BigDecimal MIN_OPENING = new BigDecimal("500.00");
    public InvestmentAccount(String accountNo, Customer owner, BigDecimal openingBalance, BigDecimal monthlyRate) {
        super(accountNo, owner, openingBalance); this.monthlyRate = monthlyRate;
        if (getBalance().compareTo(MIN_OPENING) < 0) throw new BankExceptions.MinimumOpeningDeposit(MIN_OPENING);
    }
    @Override public BigDecimal calculateInterest() { return getBalance().multiply(monthlyRate); }
}
