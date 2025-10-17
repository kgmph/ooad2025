package com.bank.port;
import com.bank.domain.Customer; import java.util.Optional;
public interface AuthService {
    Optional<Customer> authenticate(String username, String password);
    boolean registerNewUser(String name, String username, String password, String email, String phone);
}
