package edu.lakeland.insuranceclaim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DriversAdapter extends RecyclerView.Adapter{
    private ArrayList<Driver> driversData;
    private View.OnClickListener mOnItemClickListener;
    private Context parentContext;
    public static final String TAG ="DriverAdapter";

    public class DriverViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDriver;

        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDriver = itemView.findViewById(R.id.tvDriversCovered);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
        public TextView getDriverTextView() {
            return textViewDriver;
        }
    }

    public DriversAdapter(ArrayList<Driver> arrayList, Context context) {
        driversData = arrayList;
        parentContext = context;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_drivers, parent, false);
        return new DriverViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        DriverViewHolder cvh = (DriverViewHolder) holder;
        cvh.getDriverTextView().setText(driversData.get(position).getFullName());
    }

    @Override
    public int getItemCount() {
        return driversData.size();
    }

}

