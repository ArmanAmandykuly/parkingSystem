package com.example.parkingapp.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkingapp.R;
import com.example.parkingapp.model.ParkingUnit;

import java.util.List;

public class BlockRecyclerViewAdapter extends RecyclerView.Adapter<BlockRecyclerViewAdapter.ViewHolder> {
    private List<ParkingUnit> itemList;

    public BlockRecyclerViewAdapter(List<ParkingUnit> parkingUnits) {
        this.itemList = parkingUnits;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setData(List<ParkingUnit> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.id.block_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(itemList.get(position).isBooked()) {
            colorBooked(holder);
        } else {
            colorFree(holder);
        }
    }

    @Override
    public int getItemCount() {
        if(itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    public void colorBooked(@NonNull ViewHolder holder) {
        Drawable drawable = holder.itemView.getContext().getDrawable(R.drawable.booked_border_background);
        holder.itemView.setBackground(drawable);
    }

    public void colorFree(@NonNull ViewHolder holder) {
        Drawable drawable = holder.itemView.getContext().getDrawable(R.drawable.free_border_background);
        holder.itemView.setBackground(drawable);
    }
}
