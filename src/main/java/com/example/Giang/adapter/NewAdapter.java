package com.example.Giang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Giang.hosme.MainNew;
import com.example.Giang.hosme.New1;
import com.example.Giang.hosme.New2;
import com.example.Giang.hosme.New3;
import com.example.Giang.hosme.New4;
import com.example.Giang.model.New;
import com.example.finalproject_hosme.R;

import java.util.ArrayList;
import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHolder> {
    Context context;
    List<New> news;

    public NewAdapter(Context context, List<New> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public NewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_new_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewAdapter.ViewHolder holder, int position) {
        holder.imvThumb.setImageResource(news.get(position).getNewThumb());
        holder.txtName.setText(news.get(position).getNewName());
        holder.txtAuthor.setText(news.get(position).getNewAuthor());
        holder.txtTime.setText(news.get(position).getNewTime());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvThumb;
        TextView txtName;
        TextView txtAuthor;
        TextView txtTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvThumb = itemView.findViewById(R.id.imvNewRecycler);
            txtName = itemView.findViewById(R.id.txtNameNewRecycler);
            txtAuthor = itemView.findViewById(R.id.txtNameAuthorRecycler);
            txtTime = itemView.findViewById(R.id.txtTimeNewRecycler);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        New clickedNew = news.get(position);

                        if (position == 0) {
                            Intent intent = new Intent(context, New1.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else if (position == 1) {
                            Intent intent = new Intent(context, New2.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else if (position == 2) {
                            Intent intent = new Intent(context, New3.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else if (position == 3) {
                            Intent intent = new Intent(context, New4.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                }
            });

        }
    }
}
