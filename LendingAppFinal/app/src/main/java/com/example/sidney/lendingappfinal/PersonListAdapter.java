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

public class PersonListAdapter extends BaseAdapter {
    ArrayList<Integer> p_id;
    ArrayList<String> fname;
    ArrayList<String> mname;
    ArrayList<String> lname;
    ArrayList<String> type;
    ArrayList<String> status;
    ArrayList<Double> credit;
    Context context;

    public PersonListAdapter(ArrayList<Integer> p_id, ArrayList<String> fname, ArrayList<String> mname, ArrayList<String> lname, ArrayList<String> type, ArrayList<String> status,
                             ArrayList<Double> credit, Context context) {
        this.p_id = p_id;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.type = type;
        this.status = status;
        this.credit= credit;
        this.context = context;
    }

    @Override
    public int getCount() {
        return p_id.size();
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
            convertView=inflater.inflate(R.layout.personitem,null);

            holder.ID=convertView.findViewById(R.id.p_id1);
            holder.NAME=convertView.findViewById(R.id.p_name1);
            holder.TYPE=convertView.findViewById(R.id.p_type1);
            holder.STATUS=convertView.findViewById(R.id.p_status1);
            holder.CREDIT=convertView.findViewById(R.id.p_credit1);

            convertView.setTag(holder);

        }else{holder= (Holder) convertView.getTag();}

        holder.ID.setText(p_id.get(position).toString());
        holder.NAME.setText(fname.get(position)+" "+mname.get(position)+" "+lname.get(position));
        holder.TYPE.setText(type.get(position));
        holder.STATUS.setText(status.get(position));
        holder.CREDIT.setText(credit.get(position).toString());

        return convertView;
    }

    public class Holder{
        TextView ID, NAME, TYPE, STATUS, CREDIT;
    }
}
