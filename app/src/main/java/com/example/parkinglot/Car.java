/*
@author Jiating Su <jiating.su@stonybrook.edu>
Course: CSE 390: Mobile APP Development
SBU ID: 111665989
 */

package com.example.parkinglot;

/*
Car class with only empty constructor, getters and setters.
 */
public class Car {
    private int carID, time, accountID;
    private String licensePlate, brand, model, color;

    public Car() {carID = -1;}

    public int getCarID() {return carID;}
    public void setCarID(int id) {carID = id;}
    public int getAccountID() {return accountID;}
    public void setAccountID(int id) {accountID = id;}
    public int getTime() {return time;}
    public void setTime(int time) {this.time = time;}
    public String getLicensePlate() {return licensePlate;}
    public void setLicensePlate(String licensePlate) {this.licensePlate = licensePlate;}
    public String getBrand() {return brand;}
    public void setBrand(String brand) {this.brand = brand;}
    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}
    public String getColor() {return color;}
    public void setColor(String color) {this.color = color;}
}
