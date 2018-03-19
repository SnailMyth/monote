package com.myth.service;

import com.myth.base.BaseException;
import com.myth.base.Errors;
import com.myth.dao.AccountDao;
import com.myth.dao.UserDao;
import com.myth.domain.Account;
import com.myth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountService implements UserDetailsService{

    @Autowired
    private AccountDao dao;
    @Autowired
    private UserDao userDao;

    public Account getInfo(String name) {
        Account account = dao.getAccountInfo(name);
        return account;
    }

    public Account aadAccount(Account account,@Nullable User user) {
        if (user == null){
            user = new User();
            user.setNickName("myth_hai");
            user.setEmail("89938298@qq.com");
            user.setSex(true);
        }
        User save = userDao.save(user);
        account.setUser(user);
        Account account1 = dao.save(account);

        return account1;
    }

    public List<Account> getPageAccount(int page) {
        Account account = new Account();
        account.setSize(2);
        account.setPage(page);
        Sort.Direction direction = Sort.Direction.ASC.toString().equalsIgnoreCase(account.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = new Sort(direction,account.getSidx());
        PageRequest request = new PageRequest(account.getPage() - 1, account.getSize(),sort);
        return dao.findAll(request).getContent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = dao.getAccountInfo(username);
        if (null == user){
            throw BaseException.create(Errors.FILE_NULL,"username");
        }
        return user;
    }
}
