/*
@author Jiating Su <jiating.su@stonybrook.edu>
Course: CSE 390: Mobile APP Development
SBU ID: 111665989
 */

package com.example.parkinglot;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ParkingLotDBHelper extends SQLiteOpenHelper {
    //Initialize Variable
    private static final String DATABASE_NAME = "accountInfo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String ACCOUNT_TABLE = "account";
    private static final String CAR_TABLE = "car";

    //Set up tables structure
    private static final String CREATE_TABLE_ACCOUNT =
            "create table " + ACCOUNT_TABLE + " (_id integer primary key autoincrement," +
                    "username text not null, password text not null);";
    private static final String CREATE_TABLE_CAR =
            "create table " + CAR_TABLE + " (_id integer primary key autoincrement," +
                    "licenseplate text, brand text, model text, color text, time integer);";

    public ParkingLotDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_CAR);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ParkingLotDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CAR_TABLE);
        onCreate(db);
    }
}
