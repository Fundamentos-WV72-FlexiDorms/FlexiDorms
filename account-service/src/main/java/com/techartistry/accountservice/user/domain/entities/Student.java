package com.techartistry.accountservice.user.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Student extends User {
    @Column(length = 50)
    private String university;

    public Student() {}
}
