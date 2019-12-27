package com.example.login;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MaladieAdapterModel extends ArrayAdapter<MaladieListViewModel> {

    private Activity context;
    private ArrayList<MaladieListViewModel> maladieArrayList ;

    public MaladieAdapterModel(Activity context , ArrayList<MaladieListViewModel> maladieArrayList) {
        super(context, R.layout.maladies_list_view, maladieArrayList);
        this.context = context;
        this.maladieArrayList = maladieArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r = convertView;
        MaladieAdapterModel.ViewHolder viewHolder = null;
        if (r == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.maladies_list_view,null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }else{
            viewHolder = (MaladieAdapterModel.ViewHolder) r.getTag();
        }
        viewHolder.tvmaladiename.setText(maladieArrayList.get(position).getMaladiename());
        viewHolder.tvmaladienum.setText(maladieArrayList.get(position).getMaladienum());
        return r;
    }
    class ViewHolder{
        TextView tvmaladiename , tvmaladienum;

        public ViewHolder(View v) {
            tvmaladiename = v.findViewById(R.id.tvMaladieNameee);
            tvmaladienum = v.findViewById(R.id.tvMaladieNum);
        }
    }
}
