package com.myth.domain.note;

import com.myth.base.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "notes")
public class Notes extends BaseEntity implements Serializable {

    @Id
    @Column(name = "n_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "n_name")
    private String name;

    @Column(name = "n_sub")
    private String subscription;

    public Notes(String name, String subscription) {
        this.name = name;
        this.subscription = subscription;
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

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }


    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subscription='" + subscription + '\'' +
                '}';
    }
}
