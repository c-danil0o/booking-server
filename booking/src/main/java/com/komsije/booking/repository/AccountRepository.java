package com.komsije.booking.repository;

import com.komsije.booking.model.Account;
import com.komsije.booking.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountByAccountType(AccountType type);
    List<Account> findAccountByIsBlocked(boolean isBlocked);

}
