package com.example.parkinglot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText usernameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCreateAccount();
        initLoginButton();
    }

    private void initLoginButton() {
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginAccountButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(usernameInput.getText().toString(), passwordInput.getText().toString());
            }
        });
    }

    public void login(String username, String password) {
        ParkingLotDataSource ds = new ParkingLotDataSource(this);
        try {
            ds.open();
            ArrayList<Account> accounts = ds.getAccounts("username", "ASC");
            ds.close();
            for (Account account:accounts) {
                if (account.getUsername().equals(username)
                    && account.getPassword().equals(password)) {
                    Intent intent = new Intent(MainActivity.this, TableActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("account_id", account.getAccountID());
                    startActivity(intent);
                    return;
                }
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Error occurs while retrieving accounts", Toast.LENGTH_LONG).show();
        }
    }

    private void initCreateAccount() {
        ImageButton createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


}