package com.mrshadat.bdfinanceinternshipapp.models;

public class CustomerInfo {

    String name, mobile, address, profession, netWorth, birthDate, img;

    public CustomerInfo() {

    }

    public CustomerInfo(String name, String mobile, String address, String profession, String netWorth, String birthDate, String img, boolean makeDeposit) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.profession = profession;
        this.netWorth = netWorth;
        this.birthDate = birthDate;
        this.img = img;
    }

    public CustomerInfo(String name, String mobile, String address, String profession, String netWorth, String birthDate, String img) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.profession = profession;
        this.netWorth = netWorth;
        this.birthDate = birthDate;
        this.img = img;
    }

    public CustomerInfo(String name, String mobile, String address, String profession, String netWorth, String birthDate) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.profession = profession;
        this.netWorth = netWorth;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(String netWorth) {
        this.netWorth = netWorth;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
