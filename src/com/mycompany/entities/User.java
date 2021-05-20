/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;

/**
 *
 * @author Abirn
 */
public class User {
    
    private int id;
    private String name;
    private String fname;
    private String gender;
    private int num;
    private String email;
    private String password;
    private Date birthday;
    private String image;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(int id, String name, String fname, String gender, int num, String email, String password, Date birthday, String image) {
        this.id = id;
        this.name = name;
        this.fname = fname;
        this.gender = gender;
        this.num = num;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.image = image;
    }

    public User(String name, String fname, String gender, int num, String email, String password, Date birthday, String image) {
        this.name = name;
        this.fname = fname;
        this.gender = gender;
        this.num = num;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.image = image;
    }

    public User(int id, String name, String fname, String gender, int num, String email, String password) {
        this.id = id;
        this.name = name;
        this.fname = fname;
        this.gender = gender;
        this.num = num;
        this.email = email;
        this.password = password;
    }

    public User(int id, String name, String fname, String gender, int num, String email, String password, Date birthday) {
        this.id = id;
        this.name = name;
        this.fname = fname;
        this.gender = gender;
        this.num = num;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }

    public User(int id, String name, String fname, String gender, int num, String email, String password, String image) {
        this.id = id;
        this.name = name;
        this.fname = fname;
        this.gender = gender;
        this.num = num;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public User(String name, String fname, String gender, int num, String email, String password) {
        this.name = name;
        this.fname = fname;
        this.gender = gender;
        this.num = num;
        this.email = email;
        this.password = password;
    }

    public User(String name, String fname, String gender, int num, String email, String password, Date birthday) {
        this.name = name;
        this.fname = fname;
        this.gender = gender;
        this.num = num;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }

    public User(String name, String fname, String gender, int num, String email, String password, String image) {
        this.name = name;
        this.fname = fname;
        this.gender = gender;
        this.num = num;
        this.email = email;
        this.password = password;
        this.image = image;
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

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", fname=" + fname + ", gender=" + gender + ", num=" + num + ", email=" + email + ", password=" + password + ", birthday=" + birthday + ", image=" + image + '}';
    }

    
    
}
