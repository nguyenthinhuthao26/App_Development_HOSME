package com.example.MedicalRecord.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.MedicalRecord.models.MePro;
import com.example.finalproject_hosme.R;

import java.util.List;

public class ProfileAdapter extends BaseAdapter {

    Activity context;
    int item_layout;
    List<MePro> profile;

    public ProfileAdapter(Activity context, int item_layout, List<MePro> profile) {
        this.context = context;
        this.item_layout = item_layout;
        this.profile = profile;
    }

    @Override
    public int getCount() {
        return profile.size();
    }

    @Override
    public Object getItem(int position) {
        return profile.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_layout, null);

            holder.txtPatientID = convertView.findViewById(R.id.txtPatientCode);
            holder.txtPatientName = convertView.findViewById(R.id.txtPatientName);
            holder.txtPhoneNumber = convertView.findViewById(R.id.txtPhoneNumber);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        MePro profiles = profile.get(position);
        holder.txtPatientID.setText(profiles.getPatientID());
        holder.txtPatientName.setText(profiles.getPatientName());
        holder.txtPhoneNumber.setText(profiles.getPhoneNumber());
        return convertView;
    }
    public static class ViewHolder{
        TextView txtPatientID, txtPatientName, txtPhoneNumber;
    }
}
