package com.example.parkingapp.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkingapp.R;
import com.example.parkingapp.model.Parking;
import com.example.parkingapp.model.ParkingUnit;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView2);
        }
    }

    private List<Parking> itemList;

    public RecyclerViewAdapter(List<Parking> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);

        return new ViewHolder(view);
    }

    public List<Parking> getItemList() {
        return itemList;
    }

    public void setData(List<Parking> parkingList) {
        itemList = parkingList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        for(int i = 0; i < 6; i ++) {
            int resourceId = holder.itemView.getResources().getIdentifier("block" + String.valueOf(1 + i), "id", holder.itemView.getContext().getPackageName());
            colorBooked(holder, resourceId);
        }
        ((TextView)holder.itemView.findViewById(R.id.textView2)).setText(itemList.get(position).getLocation());
        if(itemList.get(position).getParkingUnitList() == null) {
            Log.e(this.getClass().toString(), "THERE SHOULDN'T BE NULL");
            return;
        }
        List<ParkingUnit> list = itemList.get(position).getParkingUnitList();
        for(int i = 0; i < Math.min(6, list.size()); i ++) {
            int resourceId = holder.itemView.getResources().getIdentifier("block" + String.valueOf(1 + i), "id", holder.itemView.getContext().getPackageName());
            Log.d("block" + String.valueOf(i), String.valueOf(list.get(i).isBooked()));
            if(!list.get(i).isBooked())
                colorFree(holder, resourceId);

            holder.itemView.findViewById(resourceId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    view.getRootView().findViewById();
                }
            });
        }
    }

    public void colorBooked(@NonNull ViewHolder holder, int resourceId) {
        Drawable drawable = holder.itemView.getContext().getDrawable(R.drawable.booked_border_background);
        holder.itemView.findViewById(resourceId).setBackground(drawable);
    }

    public void colorFree(@NonNull ViewHolder holder, int resourceId) {
        Drawable drawable = holder.itemView.getContext().getDrawable(R.drawable.free_border_background);
        holder.itemView.findViewById(resourceId).setBackground(drawable);
    }

    @Override
    public int getItemCount() {
        if(itemList == null) {
            return 0;
        }
        return itemList.size();
    }
}
