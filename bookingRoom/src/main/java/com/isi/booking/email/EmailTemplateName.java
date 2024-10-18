package com.isi.booking.email;


import lombok.Getter;

@Getter
public enum EmailTemplateName {
    RESET_PASSWORD("reset_password.html");
    private final String name;
    EmailTemplateName(String name){
        this.name = name ;
    }
}
