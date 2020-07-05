package com.example.parkinglot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter {
    private ArrayList<Car> carData;
    private View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private Context parentContext;

    public class CarViewHolder extends RecyclerView.ViewHolder {

        public TextView textLicense, textBrand;
        public Button deteleButton;
        public CarViewHolder( View itemView) {
            super(itemView);
            textLicense = itemView.findViewById(R.id.carLicense);
            textBrand = itemView.findViewById(R.id.carBrand);
            deteleButton = itemView.findViewById(R.id.deleteButton);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getTextBrand() {return textBrand;}
        public TextView getTextLicense() {return textLicense;}
        public Button getDeteleButton() {return deteleButton;}
    }

    public CarAdapter(ArrayList<Car> list, Context context) {
        carData = list;
        parentContext = context;
    }

    public void setmOnItemClickListener(View.OnClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_car, parent, false);
        return new CarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        CarViewHolder cvh = (CarViewHolder) holder;
        cvh.getTextLicense().setText(carData.get(position).getLicensePlate());
        cvh.getTextBrand().setText(carData.get(position).getBrand());
        if (isDeleting) {
            cvh.getDeteleButton().setVisibility(View.VISIBLE);
            cvh.getDeteleButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(position);
                }
            });
        }
        else {
            cvh.getDeteleButton().setVisibility(View.INVISIBLE);
        }
    }

    public void deleteItem(int position) {
        Car car = carData.get(position);
        ParkingLotDataSource ds = new ParkingLotDataSource(parentContext);
        try {
            ds.open();
            boolean didDeleted = ds.deleteCar(car.getCarID());
            ds.close();
            if (didDeleted) {
                carData.remove(position);
                notifyDataSetChanged();
            }
            else {
                Toast.makeText(parentContext, "Delete failed!", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {

        }
    }

    public void setDelete(boolean b) {isDeleting = b;}

    @Override
    public int getItemCount() {
        return carData.size();
    }
}
