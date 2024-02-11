package edu.lakeland.insuranceclaim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AutosAdapter extends RecyclerView.Adapter{
    private ArrayList<Auto> autoData;
    private View.OnClickListener mOnItemClickListener;
    private Context parentContext;
    public static final String TAG ="AutoAdapter";

    public class AutoViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewAuto;

        public AutoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuto = itemView.findViewById(R.id.tvAutosCovered);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getVehicleTextView() {
            return textViewAuto;
        }

    }

    public AutosAdapter(ArrayList<Auto> arrayList, Context context) {
        autoData = arrayList;
        parentContext = context;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_autos, parent, false);
        return new AutoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        AutoViewHolder cvh = (AutoViewHolder) holder;
        cvh.getVehicleTextView().setText(autoData.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return autoData.size();
    }

}

