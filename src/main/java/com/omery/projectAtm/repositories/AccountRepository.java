package com.omery.projectAtm.repositories;

import com.omery.projectAtm.domain.entities.AccountEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @Query("SELECT a FROM AccountEntity a WHERE a.userEntity.id = ?1")
    List<AccountEntity> getAccounts(Long id);


    // added transactional and modifying annotation to make delete query
    @Transactional
    @Modifying
    @Query("DELETE FROM AccountEntity a WHERE a.userEntity.id = ?1")
    void deleteAccountEntitiesByUserId(Long id);
}
