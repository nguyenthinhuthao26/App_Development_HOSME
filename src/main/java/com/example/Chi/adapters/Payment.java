package com.example.Chi.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Giang.adapter.NewAdapter;
import com.example.Giang.hosme.New1;
import com.example.Giang.hosme.New2;
import com.example.Giang.hosme.New3;
import com.example.Giang.hosme.New4;
import com.example.Giang.model.New;
import com.example.finalproject_hosme.R;

import java.util.List;

public class Payment extends RecyclerView.Adapter<Payment.ViewHolder>{
    Activity activity;
    int item_tt;
    List<com.example.Chi.models.Payment> payments;

    public Payment(Activity activity, int item_tt, List<com.example.Chi.models.Payment> payments) {
        this.activity = activity;
        this.item_tt = item_tt;
        this.payments = payments;
    }

    @NonNull
    @Override
    public Payment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_tt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Payment.ViewHolder holder, int position) {
        holder.imvThumb.setImageResource(payments.get(position).getImvThumb());
        holder.txttt.setText(payments.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvThumb;
        TextView txttt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvThumb = itemView.findViewById(R.id.imvtt);
            txttt = itemView.findViewById(R.id.txttt);
        }
    }
}
