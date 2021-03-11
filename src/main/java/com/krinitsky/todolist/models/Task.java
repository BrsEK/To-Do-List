package com.krinitsky.todolist.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("isPerformed")
    private boolean isPerformed;
}
