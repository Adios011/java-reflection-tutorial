package com.muhsener98.exercises.exercise18;

import com.muhsener98.exercises.exercise12.Product;

@Roles(value = {@Role("ADMIN") , @Role("USER") , @Role("SUPER_ADMIN")})
@Role("EDITOR")
public class ProductService {

    public static void main(String[] args){
        Class<?> clazz = ProductService.class;
        Role[] roles =   clazz.getAnnotationsByType(Role.class);
        for (Role role : roles) {
            System.out.println(role.value());
        }
    }
}
