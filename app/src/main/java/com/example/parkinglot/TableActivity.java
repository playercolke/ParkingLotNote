package com.example.parkinglot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {
    private RecyclerView carList;
    private CarAdapter carAdapter;

    private ArrayList<Car> cars;
    private int account_id;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            int carId = cars.get(position).getCarID();
            int account_id = cars.get(position).getAccountID();
            Intent intent = new Intent(TableActivity.this, AddCarActivity.class);
            intent.putExtra("carId", carId);
            intent.putExtra("account_id", account_id);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        initAddButton();
        initDeleteSwitch();
        Bundle bundle = getIntent().getExtras();
        account_id = bundle.getInt("account_id");
    }

    public void onResume() {
        super.onResume();

        ParkingLotDataSource ds = new ParkingLotDataSource(this);
        try {
            ds.open();
            cars = ds.getCars("brand", "ASC", account_id);
            ds.close();
            if (cars.size() > 0) {
                carList = findViewById(R.id.rvCars);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                carList.setLayoutManager(layoutManager);
                carAdapter = new CarAdapter(cars, this);
                carAdapter.setmOnItemClickListener(onItemClickListener);
                carList.setAdapter(carAdapter);
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Error occurs while retrieving cars", Toast.LENGTH_LONG).show();
        }
    }

    public void initAddButton() {
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(TableActivity.this, AddCarActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 intent.putExtra("account_id", account_id);
                 startActivity(intent);
            }
        });
    }

    public void initDeleteSwitch() {
        Switch s = findViewById(R.id.deleteSwitch);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean status = buttonView.isChecked();
                carAdapter.setDelete(status);
                carAdapter.notifyDataSetChanged();
            }
        });
    }
}