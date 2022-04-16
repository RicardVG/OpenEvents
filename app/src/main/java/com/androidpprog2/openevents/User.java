package com.androidpprog2.openevents;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String last_name;
    private String email;
    private String password;
    private String image;
    private Integer id;

    public User(String name, String last_name, String email, String password, String image, Integer id) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImage() {
        return image;
    }

    public Integer getId() {
        return id;
    }
}
