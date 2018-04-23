package com.myth.event;

import com.myth.domain.user.Account;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.context.ApplicationEvent;

public class UserRegisterEvent extends ApplicationEvent{


    private Account account;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public UserRegisterEvent(Object source, Account account) {
        super(source);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
