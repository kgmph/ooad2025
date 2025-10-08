package com.bank.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bank.domain.exceptions.InsufficientFundsException;
import com.bank.domain.exceptions.InvalidOperationException;

public class AccountRulesTests {

    @Test
    @DisplayName("SavingsAccount: withdrawals are not allowed")
    void savings_no_withdrawals() {
        SavingsAccount s = new SavingsAccount("GABS01", new BigDecimal("1000.00"));
        assertThrows(InvalidOperationException.class, () -> s.withdraw(new BigDecimal("10.00")));
    }

    @Test
    @DisplayName("InvestmentAccount: minimum opening deposit BWP 500 enforced")
    void investment_min_opening() {
        assertThrows(InvalidOperationException.class,
            () -> new InvestmentAccount("GABS01", new BigDecimal("499.99")));
        new InvestmentAccount("GABS01", new BigDecimal("500.00"));
    }

    @Test
    @DisplayName("ChequeAccount: withdrawals allowed but cannot exceed balance")
    void cheque_withdraw_rules() {
        ChequeAccount c = new ChequeAccount("GABS01", new BigDecimal("100.00"), "Orange Botswana", "CBD");
        c.withdraw(new BigDecimal("50.00"));
        assertEquals(new BigDecimal("50.00"), c.getBalance());
        assertThrows(InsufficientFundsException.class, () -> c.withdraw(new BigDecimal("60.00")));
    }
}
