/*
@author Jiating Su <jiating.su@stonybrook.edu>
Course: CSE 390: Mobile APP Development
SBU ID: 111665989
 */

package com.example.parkinglot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCarActivity extends AppCompatActivity {

    private Car currentCar;
    private int account_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        initSaveButton();

        Bundle bundle = getIntent().getExtras();
        account_id = bundle.getInt("account_id");
        if (bundle.getInt("carId") != 0) {
            initCar(bundle.getInt("carId"));
        }
        else {
            currentCar = new Car();
            currentCar.setAccountID(account_id);
        }
    }

    /*
    @Param: Car id
    @Descriptor: when there is a car id in the bundle, this method will be called to fetch the correct car data from the database.
     */
    public void initCar(int id) {
        ParkingLotDataSource ds = new ParkingLotDataSource(this);
        try {
            ds.open();
            currentCar = ds.getSpecificCar(id);
            ds.close();
        }
        catch (Exception e) {
            Toast.makeText(this, "Load Car Failed", Toast.LENGTH_LONG).show();
        }

        EditText editLicense = findViewById(R.id.textLicensePlate);
        EditText editBrand = findViewById(R.id.textBrand);
        EditText editModel = findViewById(R.id.textModel);
        EditText editColor = findViewById(R.id.textColor);
        EditText editTime = findViewById(R.id.textTime);

        editLicense.setText(currentCar.getLicensePlate());
        editBrand.setText(currentCar.getBrand());
        editModel.setText(currentCar.getModel());
        editColor.setText(currentCar.getColor());
        editTime.setText(String.valueOf(currentCar.getTime()));
    }

    //Create an save button to save the car data.
    public void initSaveButton() {
        Button saveButton = findViewById(R.id.carSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editLicense = findViewById(R.id.textLicensePlate);
                EditText editBrand = findViewById(R.id.textBrand);
                EditText editModel = findViewById(R.id.textModel);
                EditText editColor = findViewById(R.id.textColor);
                EditText editTime = findViewById(R.id.textTime);

                currentCar.setLicensePlate(editLicense.getText().toString());
                currentCar.setBrand(editBrand.getText().toString());
                currentCar.setModel(editModel.getText().toString());
                currentCar.setColor(editColor.getText().toString());
                currentCar.setTime(Integer.parseInt(editTime.getText().toString()));

                boolean didSucceed = false;
                ParkingLotDataSource ds = new ParkingLotDataSource(AddCarActivity.this);
                try {
                    ds.open();

                    //Check if the car is already exist in the database.
                    if (currentCar.getCarID() == -1) {
                        didSucceed = ds.insertCar(currentCar);
                        if (didSucceed) {
                            Log.d("Success", "new car has been inserted into database");
                        }
                    }
                    else {
                        didSucceed = ds.updateCar(currentCar);
                        if (didSucceed) {
                            Log.d("Success", "car in database has been updated");
                        }
                    }
                    ds.close();
                }
                catch (Exception e) {
                    didSucceed = false;
                }
                if (didSucceed) {
                    Intent intent = new Intent(AddCarActivity.this, TableActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("account_id", account_id);
                    startActivity(intent);
                }
            }
        });
    }
}