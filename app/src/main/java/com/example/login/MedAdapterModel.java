package com.example.login;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MedAdapterModel extends ArrayAdapter<MedModel> {

    private Activity context;
    private ArrayList<MedModel> medArrayList ;

    public MedAdapterModel(Activity context , ArrayList<MedModel> medArrayList) {
        super(context, R.layout.medlistview, medArrayList);
        this.context = context;
        this.medArrayList = medArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r = convertView;
        MedAdapterModel.ViewHolder viewHolder = null;
        if (r == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.medlistview,null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }else{
            viewHolder = (MedAdapterModel.ViewHolder) r.getTag();
        }
        viewHolder.tvMedName.setText(medArrayList.get(position).getMedname());
        viewHolder.tvMedNum.setText(medArrayList.get(position).getMednum());
        ;
        return r;
    }
    class ViewHolder{
        TextView tvMedName , tvMedNum;

        public ViewHolder(View v) {
            tvMedName = v.findViewById(R.id.tvmedname);
            tvMedNum = v.findViewById(R.id.tvmednum);
        }
    }
}
