package com.bank;
import com.bank.controller.*; import com.bank.domain.*; import com.bank.repo.*; import com.bank.service.BankService; import com.bank.view.*;
import javafx.application.Application; import javafx.scene.Scene; import javafx.stage.Stage; import java.math.BigDecimal;
public class App extends Application {
    private final InMemoryCustomerRepository customerRepo = new InMemoryCustomerRepository();
    private final InMemoryAccountRepository accountRepo  = new InMemoryAccountRepository();
    private final BankService bankService = new BankService(customerRepo, accountRepo);
    @Override public void start(Stage stage){
        seed(); showLogin(stage); stage.setTitle("Banking System"); stage.show();
    }
    private void showLogin(Stage stage){
        var login = new LoginController(bankService); var auth = new AuthController(bankService);
        var view = new LoginView(login, ()-> showRegister(stage, auth), customer-> showAccounts(stage, customer));
        stage.setScene(new Scene(view, 560, 320));
    }
    private void showRegister(Stage stage, AuthController auth){ stage.setScene(new Scene(new RegisterView(auth, ()-> showLogin(stage)), 560, 360)); }
    private void showAccounts(Stage stage, Customer customer){ stage.setScene(new Scene(new AccountView(customer, new AccountController(bankService)), 880, 640)); }
    private void seed(){
        Customer alice = new Customer("C001","Alice","alice@example.com","+267-71-000-000");
        bankService.registerCustomer(alice); bankService.registerUser("alice","pass123", alice);
        var savings=new SavingsAccount("SA-1", alice, new BigDecimal("1000.00"), new BigDecimal("0.0005"));
        var invest =new InvestmentAccount("IV-1", alice, new BigDecimal("500.00"),  new BigDecimal("0.05"));
        var cheque =new ChequeAccount("CQ-1", alice, new BigDecimal("200.00"),  new BigDecimal("100.00"), null);
        bankService.addAccount(savings); bankService.addAccount(invest); bankService.addAccount(cheque);
    }
    public static void main(String[] args){ launch(); }
}
