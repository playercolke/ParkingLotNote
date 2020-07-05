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
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private ImageButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initLoginAccount();
        initUsernameField();
        initPasswordField();
        initCreateButton();
    }

    private void initUsernameField() {
        usernameInput = findViewById(R.id.usernameInput);
    }

    private void initPasswordField() {
        passwordInput = findViewById(R.id.passwordInput);
    }

    //Button to move sent the user to the loginActivity(MainActivity).
    private void initLoginAccount() {
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    //Set up the create button to create an new account.
    private void initCreateButton() {
        Button createButton = findViewById(R.id.createAccountButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wasSuccessful = false;
                ParkingLotDataSource ds = new ParkingLotDataSource(CreateActivity.this);
                try {
                    ds.open();

                    ArrayList<Account> accounts = ds.getAccounts("username", "ASC");

                    Account newAccount = new Account(usernameInput.getText().toString(),
                            passwordInput.getText().toString());

                    for (Account account:accounts) {
                        if (newAccount.getUsername().equals(account.getUsername())) {
                            duplicateUsernameText();
                            return;
                        }
                    }
                    wasSuccessful = ds.insertAccount(newAccount);
                    Log.d("success", "good to go");

                    ds.close();
                }
                catch (Exception e) {
                    wasSuccessful = false;
                    Log.e("fail", "exception catched");
                }
                if (wasSuccessful) {
                    loginButton.callOnClick();
                }
            }
        });
    }

    //When the user try to create a duplicate username, this message will be printed.
    private void duplicateUsernameText() {
        Toast.makeText(this, "Duplicate Username", Toast.LENGTH_LONG).show();
    }
}