package com.myth.domain.user;

import com.myth.base.BaseEntity;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class Roles extends BaseEntity implements Serializable,GrantedAuthority {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GenericGenerator(name = "generator", strategy = "native")
    @GeneratedValue(generator="generator")
    private int id;
    @Column(name = "r_name")
    private String name;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)
    @JoinColumn(name="ownerId")
    private Account ownerId;

    public Account getAccount() {
        return ownerId;
    }

    public void setAccount(Account account) {
        this.ownerId = account;
    }

    public Roles() {
    }

    public Roles(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
