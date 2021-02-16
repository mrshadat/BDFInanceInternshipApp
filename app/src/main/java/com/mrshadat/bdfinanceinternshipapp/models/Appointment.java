package com.mrshadat.bdfinanceinternshipapp.models;

public class Appointment {

    String mobile, name, date, message;

    public Appointment() {

    }

    public Appointment(String mobile, String name, String date, String message) {
        this.mobile = mobile;
        this.name = name;
        this.date = date;
        this.message = message;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
