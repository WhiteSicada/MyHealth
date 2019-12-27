package com.example.login;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConsultationAdapterModel extends ArrayAdapter<ConsultationListViewModel> {


    private Activity context;
    private ArrayList<ConsultationListViewModel> ConsultationName ;

    public ConsultationAdapterModel(Activity context , ArrayList<ConsultationListViewModel> ConsultationName) {
        super(context, R.layout.listview_layout, ConsultationName);
        this.context = context;
        this.ConsultationName = ConsultationName;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listview_layout,null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.tvDate.setText(ConsultationName.get(position).getDate());
        viewHolder.tvName.setText(ConsultationName.get(position).getNameconsultation());

        return r;
    }
    class ViewHolder{
        TextView tvName , tvDate;

        public ViewHolder(View v) {
            tvName = v.findViewById(R.id.tvConsultationName);
            tvDate = v.findViewById(R.id.tvDate);
        }
    }
}
