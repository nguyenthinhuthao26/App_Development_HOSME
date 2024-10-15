package com.example.Giang.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Giang.model.InforDoctor;
import com.example.Giang.model.Specialists;
import com.example.finalproject_hosme.R;

import java.util.List;

public class AdapterSpecialist extends BaseAdapter {
    Activity context;
    int item_specialist;
    List<Specialists> specialists;

    public AdapterSpecialist(Activity context, int item_specialist, List<Specialists> specialists) {
        this.context = context;
        this.item_specialist = item_specialist;
        this.specialists = specialists;
    }

    @Override
    public int getCount() {
        return specialists.size();
    }

    @Override
    public Object getItem(int position) {
        return specialists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new AdapterSpecialist.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_specialist, null);
            holder.txtNameSpecialist = convertView.findViewById(R.id.txtNameSpecialist);
            holder.imvSpecialist = convertView.findViewById(R.id.imvSpecialist);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Specialists s = specialists.get(position);
        holder.txtNameSpecialist.setText(s.getSpecialistName());

        byte[] image = s.getSpecialistImage();
        if (holder.imvSpecialist != null) {
            if (image != null && image.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                holder.imvSpecialist.setImageBitmap(bitmap);
            } else {
                // Xử lý trường hợp không có hình ảnh
                holder.imvSpecialist.setImageResource(R.drawable.icon_hosme); // Đặt hình ảnh mặc định
            }
        }

        return convertView;
    }

    public static class ViewHolder{
        ImageView imvSpecialist;
        TextView txtNameSpecialist;
    }
}
