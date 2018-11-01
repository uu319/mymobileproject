package com.example.sidney.lendingappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sidney on 5/2/2018.
 */

public class LoanListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> ID_ARRAY;
    ArrayList<String> PNAME_ARRAY, SNAME_ARRAY, RNAME_ARRAY, STATUS_ARRAY;
    ArrayList<Double> BALANCE_ARRAY;

    public LoanListAdapter(Context context, ArrayList<Integer> ID_ARRAY, ArrayList<String> PNAME_ARRAY, ArrayList<String> SNAME_ARRAY, ArrayList<String> RNAME_ARRAY,
                           ArrayList<Double> BALANCE_ARRAY, ArrayList<String> STATUS_ARRAY) {
        this.context = context;
        this.ID_ARRAY = ID_ARRAY;
        this.PNAME_ARRAY = PNAME_ARRAY;
        this.SNAME_ARRAY = SNAME_ARRAY;
        this.RNAME_ARRAY = RNAME_ARRAY;
        this.BALANCE_ARRAY= BALANCE_ARRAY;
        this.STATUS_ARRAY= STATUS_ARRAY;
    }


    @Override
    public int getCount() {
        return ID_ARRAY.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Holder holder= new Holder();
        LayoutInflater layoutInflater;
        if(convertView==null){
            layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.loanitem,null);
            holder.id= convertView.findViewById(R.id.lid);
            holder.pname=convertView.findViewById(R.id.pname);
            holder.sname= convertView.findViewById(R.id.sname);
            holder.rname= convertView.findViewById(R.id.rname);
            holder.balance=convertView.findViewById(R.id.lbalance);
            holder.status=convertView.findViewById(R.id.lstatus);
            convertView.setTag(holder);

        }else holder= (Holder) convertView.getTag();
        holder.id.setText(ID_ARRAY.get(position).toString());
        holder.pname.setText(PNAME_ARRAY.get(position));
        holder.sname.setText(SNAME_ARRAY.get(position));
        holder.rname.setText(RNAME_ARRAY.get(position));
        holder.balance.setText(BALANCE_ARRAY.get(position).toString());
        holder.status.setText(STATUS_ARRAY.get(position));
        return convertView;
    }

    public class Holder{
        TextView id, pname, sname, rname, balance, status;

    }
}
