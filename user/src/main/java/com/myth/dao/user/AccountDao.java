package com.myth.dao.user;

import com.myth.base.BaseRepository;
import com.myth.domain.user.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountDao extends BaseRepository<Account,Integer> {

    @Query("select t from Account  t where t.username = :name")
    public Account getAccountInfo(@Param("name") String name);
}
