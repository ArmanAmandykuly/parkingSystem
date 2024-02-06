package com.example.parkingapp.util;

import android.util.Log;
import android.widget.Filter;

import com.example.parkingapp.adapter.RecyclerViewAdapter;
import com.example.parkingapp.model.Parking;

import java.util.ArrayList;
import java.util.List;

public class ListFilter {
    private List<Parking> itemList;
    private List<Parking> filteredList;

    private RecyclerViewAdapter adapter;

    public ListFilter(List<Parking> itemList, RecyclerViewAdapter adapter) {
        this.itemList = itemList;
        this.filteredList = adapter.getItemList();
        this.adapter = adapter;
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                filteredList.clear();
                if (filterPattern.isEmpty()) {
                    filteredList.addAll(itemList);
                } else {
                    for (Parking item : itemList) {
                        if (item.getLocation().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (List<Parking>) filterResults.values;
                adapter.notifyDataSetChanged();
            }
        };
    }

    public List<Parking> getItemList() {
        return itemList;
    }

    public List<Parking> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<Parking> filteredList) {
        this.filteredList = filteredList;
    }

    public void setItemList(List<Parking> itemList) {
        this.itemList = itemList;
    }
}
