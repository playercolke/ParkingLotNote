/*
@author Jiating Su <jiating.su@stonybrook.edu>
Course: CSE 390: Mobile APP Development
SBU ID: 111665989
 */

package com.example.parkinglot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ParkingLotDataSource {
    private SQLiteDatabase database;
    private ParkingLotDBHelper dbHelper;

    public ParkingLotDataSource(Context context) {dbHelper = new ParkingLotDBHelper(context);}

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {dbHelper.close();}

    /*
    @Param: account that needed to be insert into the account table
    @Description: insert a new account into the account table.
     */
    public boolean insertAccount(Account account) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("username", account.getUsername());
            initialValues.put("password", account.getPassword());

            didSucceed = database.insert("account", null, initialValues) > 0;
        }
        catch (Exception e) {
            //Do nothing
        }
        return didSucceed;
    }

    /*
    @Param: account that needed to be update in the account table
    @Description: update specific account in the table
     */
    public boolean updateAccount(Account account) {
        boolean didSucceed = false;
        try {
            long  rowID = (long) account.getAccountID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("username", account.getUsername());
            updateValues.put("password", account.getPassword());

            didSucceed = database.insert("account", "_id=" + rowID, updateValues) > 0;
        }
        catch (Exception e) {
            //Do nothing
        }
        return didSucceed;
    }

    /*
    @Param: car that needed to be insert into the car table
    @Description: insert a new car into the car table.
     */
    public boolean insertCar(Car car) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("licenseplate", car.getLicensePlate());
            initialValues.put("brand", car.getBrand());
            initialValues.put("model", car.getModel());
            initialValues.put("color", car.getColor());
            initialValues.put("time", car.getTime());

            didSucceed = database.insert("car", null, initialValues) > 0;
        }
        catch (Exception e) {
            //Do nothing
        }
        return didSucceed;
    }

    /*
    @Param: account that needed to be update in the account table
    @Description: update specific account in the table
     */
    public boolean updateCar(Car car) {
        boolean didSucceed = false;
        try {
            long  rowID = (long) car.getCarID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("licenseplate", car.getLicensePlate());
            updateValues.put("brand", car.getBrand());
            updateValues.put("model", car.getModel());
            updateValues.put("color", car.getColor());
            updateValues.put("time", car.getTime());

            didSucceed = database.insert("account", "_id=" + rowID, updateValues) > 0;
        }
        catch (Exception e) {
            //Do nothing
        }
        return didSucceed;
    }

    /*
    @Description: get the auto generate id of the newly insert account
     */
    public int getLastAccountId() {
        int lastId;
        try {
            String query = "SELECT MAX(_id) FROM account";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    /*
    @Description: get the auto generate id of the newly insert car
     */
    public int getLastCarId() {
        int lastId;
        try {
            String query = "SELECT MAX(_id) FROM car";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    /*
    @Params: sort field and sort method
    @Description: return a arrayList of accounts and sorted in specific way
     */
    public ArrayList<Account> getAccounts(String sortField, String sortOrder) {
        ArrayList<Account> accounts = new ArrayList<Account>();
        try {
            String query = "SELECT * FROM account ORDER BY " + sortField + " " + sortOrder;
            Cursor cursor = database.rawQuery(query, null);

            Account newAccount;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newAccount = new Account();
                newAccount.setAccountID(cursor.getInt(0));
                newAccount.setUsername(cursor.getString(1));
                newAccount.setPassword(cursor.getString(2));

                //add to arrayList
                accounts.add(newAccount);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            accounts = new ArrayList<Account>();
        }
        return accounts;
    }

    /*
    @Params: sort field and sort method
    @Description: return a arrayList of cars and sorted in specific way
     */
    public ArrayList<Car> getCars(String sortField, String sortOrder) {
        ArrayList<Car> cars = new ArrayList<Car>();
        try {
            String query = "SELECT * FROM car ORDER BY " + sortField + " " + sortOrder;
            Cursor cursor = database.rawQuery(query, null);

            Car newCar;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newCar = new Car();
                newCar.setCarID(cursor.getInt(0));
                newCar.setLicensePlate(cursor.getString(1));
                newCar.setBrand(cursor.getString(2));
                newCar.setModel(cursor.getString(3));
                newCar.setColor(cursor.getString(4));
                newCar.setTime(cursor.getInt(5));

                //add to arrayList
                cars.add(newCar);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            cars = new ArrayList<Car>();
        }
        return cars;
    }

    /*
    @Param: accountID
    @Description: to get a specific account by given id
     */
    public Account getSpecificAccount(int accountId) {
        Account account = new Account();
        String query = "SELECT * FROM account WHERE _id =" + accountId;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            account.setAccountID(cursor.getInt(0));
            account.setUsername(cursor.getString(1));
            account.setPassword(cursor.getString(2));
        }

        cursor.close();
        return account;
    }

    /*
    @Param: carID
    @Description: to get a specific car by given id
     */
    public Car getSpecificCar(int carId) {
        Car car = new Car();
        String query = "SELECT * FROM car WHERE _id =" + carId;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            car.setCarID(cursor.getInt(0));
            car.setLicensePlate(cursor.getString(1));
            car.setBrand(cursor.getString(2));
            car.setModel(cursor.getString(3));
            car.setColor(cursor.getString(4));
            car.setTime(cursor.getInt(5));
        }
        cursor.close();
        return car;
    }

    /*
    @Param: carID
    @Description: delete the car by given ID
     */
    public boolean deleteCar(int carId) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("car", "_id=" + carId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing
        }
        return didDelete;
    }
}
