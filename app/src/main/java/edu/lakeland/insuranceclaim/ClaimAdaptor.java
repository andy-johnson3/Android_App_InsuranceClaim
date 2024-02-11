package edu.lakeland.insuranceclaim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClaimAdaptor extends RecyclerView.Adapter{
    private ArrayList<Claim> claimData;
    private View.OnClickListener mOnItemClickListener;
    private Context parentContext;
    public static final String TAG ="ClaimAdaptor";

    public class ClaimViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDate;
        public TextView textViewVehicle;
        public TextView textViewDriver;

        public ClaimViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.tvClaimDate);
            textViewVehicle = itemView.findViewById(R.id.tvVehicle);
            textViewDriver = itemView.findViewById(R.id.tvDriver);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getDateTextView(){ return textViewDate; }
        public TextView getVehicleTextView() {
            return textViewVehicle;
        }
        public TextView getDriverTextView() {
            return textViewDriver;
        }
    }

    public ClaimAdaptor(ArrayList<Claim> arrayList, Context context) {
        claimData = arrayList;
        parentContext = context;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ClaimViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ClaimViewHolder cvh = (ClaimViewHolder) holder;
        cvh.getDateTextView().setText(claimData.get(position).getClaimDate());
        cvh.getVehicleTextView().setText(claimData.get(position).getAuto());
        cvh.getDriverTextView().setText(claimData.get(position).getDriverName());
    }

    @Override
    public int getItemCount() {
        return claimData.size();
    }

}

