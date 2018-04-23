package com.myth.service;

import com.myth.base.BaseException;
import com.myth.base.Errors;
import com.myth.dao.user.AccountDao;
import com.myth.dao.user.RolesDao;
import com.myth.dao.user.UserDao;
import com.myth.domain.user.Account;
import com.myth.domain.user.Roles;
import com.myth.domain.user.User;
import com.myth.event.UserRegisterEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountDao dao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RolesDao rolesDao;

    @Autowired
    private ApplicationContext applicationContext;

    public Account getInfo(String name) {
        Account account = dao.getAccountInfo(name);
        return account;
    }

    public List<Account> getPageAccount(int page) {
        Account account = new Account();
        account.setSize(2);
        account.setPage(page);
        Sort.Direction direction = Sort.Direction.ASC.toString().equalsIgnoreCase(account.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = new Sort(direction, account.getSidx());
        PageRequest request = new PageRequest(account.getPage() - 1, account.getSize(), sort);
        return dao.findAll(request).getContent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = dao.getAccountInfo(username);
        if (null == user) {
            throw BaseException.create(Errors.FIND_USER_FAIL, "username");
        }
        System.out.println("user:" + user);
        return user;
    }


    /**
     * 添加用户
     *
     * @param account
     * @return
     */
    public Account addAccount(Account account) {
        Roles user = new Roles("USER");
        account.getRoles().add(user);
        account.actvice();
        user.setAccount(account);
        Account saveAccount = dao.save(account);
        applicationContext.publishEvent(new UserRegisterEvent(this,account));
        return saveAccount;
    }

    /**
     * 删除用户
     *
     * @param name
     */
    public void deleteAccount(String name) {
        Account account = dao.getAccountInfo(name);
        if (account != null) {
            List<Roles> roles = account.getRoles();
            for (Roles r : roles) {
                rolesDao.delete(r);
            }
            dao.delete(account);
        }
    }
}
