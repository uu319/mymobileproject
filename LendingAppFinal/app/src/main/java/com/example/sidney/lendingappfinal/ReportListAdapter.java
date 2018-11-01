package com.example.sidney.lendingappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sidney on 5/27/2018.
 */

public class ReportListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> REPORT_ID;
    ArrayList<Integer> REPORT_ACTION_ID;
    ArrayList<String> REPORT_DATE;
    ArrayList<String> REPORT_ACTION;

    public ReportListAdapter(Context context, ArrayList<Integer> REPORT_ID, ArrayList<Integer> REPORT_ACTION_ID, ArrayList<String> REPORT_DATE, ArrayList<String> REPORT_ACTION) {
        this.context = context;
        this.REPORT_ID = REPORT_ID;
        this.REPORT_ACTION_ID = REPORT_ACTION_ID;
        this.REPORT_DATE = REPORT_DATE;
        this.REPORT_ACTION = REPORT_ACTION;
    }

    @Override
    public int getCount() {
        return REPORT_ID.size();
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
        LayoutInflater inflater;
        Holder holder= new Holder();
        if(convertView==null){
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.reportitem, null);
            holder.r_id= convertView.findViewById(R.id.r_id);
            holder.r_date= convertView.findViewById(R.id.r_date);
            holder.r_action= convertView.findViewById(R.id.r_action);
            holder.r_acion_id= convertView.findViewById(R.id.r_action_id);

            convertView.setTag(holder);
        }else holder= (Holder) convertView.getTag();
            holder.r_id.setText(REPORT_ID.get(position).toString());
            holder.r_date.setText(REPORT_DATE.get(position));
            holder.r_action.setText(REPORT_ACTION.get(position));
            holder.r_acion_id.setText(REPORT_ACTION_ID.get(position).toString());

        return convertView;
    }

    public class Holder{
        TextView r_id,r_date, r_action, r_acion_id;
    }
}
