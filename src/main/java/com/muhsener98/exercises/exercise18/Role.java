package com.muhsener98.exercises.exercise18;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Roles.class)
public @interface Role {
    String value();
}
