package com.nta.sqlite;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class adapterCV extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private ArrayList<congViec> congViecArrayList;

    public adapterCV(MainActivity context, int layout, ArrayList<congViec> congViecArrayList) {
        this.context = context;
        this.layout = layout;
        this.congViecArrayList = congViecArrayList;
    }

    @Override
    public int getCount() {
        return congViecArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class viewHolder{
        TextView tvDescription;
        ImageView imgDelete,imgEdit;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder;
        if(view == null){
            holder = new viewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.tvDescription = (TextView) view.findViewById(R.id.idDescripsionTV);
            holder.imgDelete = (ImageView) view.findViewById(R.id.idDeleteIMG);
            holder.imgEdit = (ImageView) view.findViewById(R.id.idEditIMG);

            view.setTag(holder);
        }else{
            holder = (viewHolder) view.getTag();
        }
        String viec = congViecArrayList.get(i).getDescription();
        holder.tvDescription.setText(viec);

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.setDialogDelete(congViecArrayList.get(i).getId());
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.setDialogEdit(viec,congViecArrayList.get(i).getId());
            }
        });
        return view;
    }
}
