package com.example.sidney.lendingappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sidney.lendingappfinal.R;

import java.util.ArrayList;

/**
 * Created by Sidney on 5/26/2018.
 */

public class AttendanceListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> ATT_ID;
    ArrayList<String> ATT_DATE;
    ArrayList<Double> ATT_AMOUNTEXPECTED;
    ArrayList<String> ATT_STATUS;
    ArrayList<Double> ATT_PAID;
    public AttendanceListAdapter(Context context, ArrayList<Integer> ATT_ID, ArrayList<String> ATT_DATE,
                                 ArrayList<Double> ATT_AMOUNTEXPECTED, ArrayList<String> ATT_STATUS
                                ,ArrayList<Double> ATT_PAID) {
        this.context = context;
        this.ATT_ID = ATT_ID;
        this.ATT_DATE = ATT_DATE;
        this.ATT_AMOUNTEXPECTED = ATT_AMOUNTEXPECTED;
        this.ATT_STATUS = ATT_STATUS;
        this.ATT_PAID= ATT_PAID;
    }


    @Override
    public int getCount() {
        return ATT_ID.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder= new Holder();
        LayoutInflater inflater;
        if(convertView==null){
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.attendanceitem, null);
            holder.att_id= convertView.findViewById(R.id.att_id);
            holder.att_date= convertView.findViewById(R.id.att_date);
            holder.att_amountexpect= convertView.findViewById(R.id.att_amountExpec);
            holder.att_status= convertView.findViewById(R.id.att_status);

            holder.att_paid= convertView.findViewById(R.id.att_paid);

            convertView.setTag(holder);
        }else holder= (Holder) convertView.getTag();

        holder.att_id.setText(ATT_ID.get(position).toString());
        holder.att_date.setText(ATT_DATE.get(position));
        holder.att_amountexpect.setText(ATT_AMOUNTEXPECTED.get(position).toString());
        holder.att_status.setText(ATT_STATUS.get(position));
        holder.att_paid.setText(ATT_PAID.get(position).toString());
        return convertView;
    }

    public class Holder{
        TextView att_id, att_date, att_amountexpect, att_status,att_paid;
    }
}
