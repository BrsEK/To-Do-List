package com.krinitsky.todolist.models;


import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
public class Identify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
