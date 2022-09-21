package com.example.xenovex;

public class UserModel {
    private int id;
    private String uName;
    private String uMobile;
    private String uEmail;
    private String UAddress;

    public UserModel(int id, String uName, String uMobile, String uEmail, String UAddress) {
        this.id = id;
        this.uName = uName;
        this.uMobile = uMobile;
        this.uEmail = uEmail;
        this.UAddress = UAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuMobile() {
        return uMobile;
    }

    public void setuMobile(String uMobile) {
        this.uMobile = uMobile;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getUAddress() {
        return UAddress;
    }

    public void setUAddress(String UAddress) {
        this.UAddress = UAddress;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", uName='" + uName + '\'' +
                ", uMobile='" + uMobile + '\'' +
                ", uEmail='" + uEmail + '\'' +
                ", UAddress='" + UAddress + '\'' +
                '}';
    }
}
