package org.example.Models;

import java.sql.Date;

public class Employee {
    private int id;
    private String name;
    private String age;
    private String login;
    private String password;
    private Date work_date;
    private int inn;
    private int salary;
    private Date birthday_date;
    private String mobile_number;
    private String e_mail;
    private int total;

    public Employee(){

    }

    public Employee(int id, String name, String age, String login, String password, Date work_date, int inn,
                    int salary, Date birthday_date, String mobile_number, String e_mail, int total) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.login = login;
        this.password = password;
        this.work_date = work_date;
        this.inn = inn;
        this.salary = salary;
        this.birthday_date = birthday_date;
        this.mobile_number = mobile_number;
        this.e_mail = e_mail;
        this.total = total;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getWork_date() {
        return work_date;
    }

    public void setWork_date(Date work_date) {
        this.work_date = work_date;
    }

    public int getInn() {
        return inn;
    }

    public void setInn(int inn) {
        this.inn = inn;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getBirthday_date() {
        return birthday_date;
    }

    public void setBirthday_date(Date birthday_date) {
        this.birthday_date = birthday_date;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
