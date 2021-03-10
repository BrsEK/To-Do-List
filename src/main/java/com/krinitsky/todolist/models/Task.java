package com.krinitsky.todolist.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Getter
@Setter
public class Task extends Identify {

    @NotNull
    private String text;
    @NotNull
    private boolean isPerformed;
}
