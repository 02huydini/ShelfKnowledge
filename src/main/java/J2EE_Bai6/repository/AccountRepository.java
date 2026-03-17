package J2EE_Bai6.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import J2EE_Bai6.models.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT a FROM Account a WHERE a.login_name = :login_name")
    Optional<Account> findByLoginName(String login_name);
}
