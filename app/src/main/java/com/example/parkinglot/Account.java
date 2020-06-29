/*
@author Jiating Su <jiating.su@stonybrook.edu>
Course: CSE 390: Mobile APP Development
SBU ID: 111665989
 */

package com.example.parkinglot;

public class Account {
    private int accountID;
    private String username, password;

    public Account() {
        accountID = -1;
    }


    public int getAccountID() {return accountID;}
    public void setAccountID(int id) {accountID = id;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}