package com.bank.view;
import com.bank.controller.AccountController; import com.bank.domain.Account; import com.bank.domain.Customer;
import javafx.geometry.*; import javafx.scene.control.*; import javafx.scene.layout.*; import java.math.BigDecimal; import java.text.NumberFormat; import java.util.*; import java.util.Locale;
public class AccountView extends VBox {
    private final FlowPane tiles = new FlowPane(14,14);
    private final TextField fromAccField = new TextField(); private final TextField amount = new TextField(); private final TextField note = new TextField();
    private final Label status = new Label(); private final NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("en","BW"));
    private Account selectedTarget = null;
    public AccountView(Customer customer, AccountController controller){
        setSpacing(18); setPadding(new Insets(22));
        Label title=new Label("Welcome, "+customer.getName()); title.setStyle("-fx-font-size:20px;-fx-font-weight:bold;");
        tiles.setPrefWrapLength(760); refreshTiles(controller, customer);
        fromAccField.setPromptText("From Acc (e.g., CQ-1)"); amount.setPromptText("Amount"); note.setPromptText("Note (optional)");
        Button depositSel=new Button("Deposit into Selected"); Button withdrawSel=new Button("Withdraw from Selected"); Button transferToSel=new Button("Transfer From (typed) → Selected");
        Button applyInt=new Button("Apply Monthly Interest"); Button refresh=new Button("Refresh");
        HBox line1=new HBox(10, new Label("From Acc:"), fromAccField, new Label("Amount:"), amount); HBox line2=new HBox(10, new Label("Note:"), note);
        HBox line3=new HBox(10, depositSel, withdrawSel, transferToSel, applyInt, refresh); line1.setAlignment(Pos.CENTER_LEFT); line2.setAlignment(Pos.CENTER_LEFT);
        depositSel.setOnAction(e->{ if(!ensureTarget()) return; try{ var res=controller.deposit(selectedTarget.getAccountNo(), new BigDecimal(amount.getText()), note.getText());
            statusOk("Deposited "+money.format(res.amount())+" into "+friendly(selectedTarget)+"."); refreshTiles(controller, customer);}catch(Exception ex){statusErr(ex.getMessage());}});
        withdrawSel.setOnAction(e->{ if(!ensureTarget()) return; try{ var res=controller.withdraw(selectedTarget.getAccountNo(), new BigDecimal(amount.getText()), note.getText());
            statusOk("Withdrew "+money.format(res.amount())+" from "+friendly(selectedTarget)+"."); refreshTiles(controller, customer);}catch(Exception ex){statusErr(ex.getMessage());}});
        transferToSel.setOnAction(e->{ if(!ensureTarget()) return; String fromId=fromAccField.getText(); if(fromId==null||fromId.isBlank()){ statusErr("Type the 'From Acc' (e.g., CQ-1)."); return; }
            try{ var res=controller.transfer(fromId.trim(), selectedTarget.getAccountNo(), new BigDecimal(amount.getText()), note.getText()); var from=controller.getAccount(fromId.trim());
                statusOk("Transferred "+money.format(res.amount())+" from "+friendly(from)+" to "+friendly(selectedTarget)+"."); refreshTiles(controller, customer);}catch(Exception ex){statusErr(ex.getMessage());}});
        applyInt.setOnAction(e->{ try{ controller.applyMonthlyInterest(); statusOk("Applied monthly interest to eligible accounts."); refreshTiles(controller, customer);}catch(Exception ex){statusErr(ex.getMessage());}});
        refresh.setOnAction(e-> refreshTiles(controller, customer)); status.setStyle("-fx-text-fill:#0a0;");
        getChildren().addAll(title, new Label("Your Accounts:"), tiles, new Separator(), line1, line2, line3, status);
    }
    private void refreshTiles(AccountController controller, Customer c){ List<Account> accounts = controller.listAccounts(c); tiles.getChildren().clear();
        if(accounts.isEmpty()){ tiles.getChildren().add(new Label("No accounts yet for this user.")); } else { for(Account a:accounts) tiles.getChildren().add(makeTile(a)); } }
    private Region makeTile(Account a){ VBox card=new VBox(6); card.setPadding(new Insets(14)); card.setStyle(cardStyle(false)); Label header=new Label(friendly(a)); header.setStyle("-fx-font-weight:bold;");
        Label bal=new Label("Balance: "+money.format(a.getBalance())); card.getChildren().addAll(header, bal); card.setPrefWidth(220);
        card.setOnMouseClicked(e->{ selectedTarget=a; tiles.getChildren().forEach(n-> n.setStyle(cardStyle(false))); card.setStyle(cardStyle(true)); statusOk("Selected target: "+friendly(a));});
        return card; }
    private String cardStyle(boolean sel){ String base="-fx-background-color:#f7f7f7; -fx-border-color:#ddd; -fx-border-radius:10; -fx-background-radius:10;"; String hi=" -fx-border-color:#2b7; -fx-border-width:2;"; return base+(sel?hi:""); }
    private boolean ensureTarget(){ if(selectedTarget==null){ statusErr("Click a tile to select the target account first."); return false; } return true; }
    private void statusOk(String m){ status.setStyle("-fx-text-fill:#0a0;"); status.setText(m); } private void statusErr(String m){ status.setStyle("-fx-text-fill:#a00;"); status.setText(m); }
    private String friendly(Account a){ String t=a.getClass().getSimpleName().replace("SavingsAccount","Savings Account").replace("InvestmentAccount","Investment Account").replace("ChequeAccount","Cheque Account"); return t+" ("+a.getAccountNo()+")"; }
}
