package com.dbeqiraj.guideapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dbeqiraj.guideapp.R;
import com.dbeqiraj.guideapp.model.Forecaste;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Forecaste> mData;


    public RecyclerViewAdapter(Context context, ArrayList<Forecaste> mData) {
        this.context = context;
        this.mData = mData;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

        View view;
        LayoutInflater inflater= LayoutInflater.from(context);
        view=inflater.inflate(R.layout.forecast_row_design,parent,false);

        final MyViewHolder viewHolder=new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int high=mData.get(position).getHigh();
        int low=mData.get(position).getLow();

        DecimalFormat value = new DecimalFormat("##");


        if(high==0){
            holder.tv_day.setText(mData.get(position).getDay()+", "+mData.get(position).getDate());
            holder.tv_high.setText("_ "+"°C");
            holder.tv_low.setText(value.format((low-32)*0.5556)+"°C");
            holder.tv_text.setText(mData.get(position).getText());
        }
        else {
            holder.tv_day.setText(mData.get(position).getDay()+", "+mData.get(position).getDate());
            holder.tv_high.setText(value.format((high-32)*0.5556)+"°C");
            holder.tv_low.setText(value.format((low-32)*0.5556)+"°C");
            holder.tv_text.setText(mData.get(position).getText());
        }








    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView tv_day,tv_high,tv_low,tv_text;



        public MyViewHolder(View itemView) {
            super(itemView);



            tv_day = itemView.findViewById(R.id.tvDay);
            tv_high = itemView.findViewById(R.id.tvHigh);
            tv_low = itemView.findViewById(R.id.tvLow);
            tv_text=itemView.findViewById(R.id.tvText);


        }
    }
}
