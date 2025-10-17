# bankingSystem(javafx) – interfaces

JavaFX GUI + clean architecture with **5 Java interfaces**:
1. `com.bank.domain.InterestBearing` (domain)
2. `com.bank.port.AuthService` (port)
3. `com.bank.port.AccountService` (port)
4. `com.bank.repo.AccountRepository` (repo)
5. `com.bank.repo.CustomerRepository` (repo)

Boundary (GUI) classes (no business logic): `LoginView`, `RegisterView`, `AccountView` under `src/main/java/com/bank/view/`.

## Requirements
- **JDK 17**
- **Maven 3.8+**
(No separate JavaFX install needed; the plugin downloads the modules.)

## Run (terminal)
From this folder:
```bash
mvn -q javafx:run
