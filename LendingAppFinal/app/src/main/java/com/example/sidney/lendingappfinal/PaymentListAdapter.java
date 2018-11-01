package com.example.sidney.lendingappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sidney on 5/10/2018.
 */

public class PaymentListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> PAYMENT_ID;
    ArrayList<String> PAYMENT_OWNER, PAYMENT_RECEIVER, PAYMENT_DATE;
    ArrayList<Double> PAYMENT_AMOUNT;

    public PaymentListAdapter(Context context, ArrayList<Integer> PAYMENT_ID, ArrayList<String> PAYMENT_OWNER, ArrayList<String> PAYMENT_RECEIVER, ArrayList<String> PAYMENT_DATE, ArrayList<Double> PAYMENT_AMOUNT) {
        this.context = context;
        this.PAYMENT_ID = PAYMENT_ID;
        this.PAYMENT_OWNER = PAYMENT_OWNER;
        this.PAYMENT_RECEIVER = PAYMENT_RECEIVER;
        this.PAYMENT_DATE = PAYMENT_DATE;
        this.PAYMENT_AMOUNT = PAYMENT_AMOUNT;
    }

    @Override
    public int getCount() {
        return PAYMENT_ID.size();
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
        LayoutInflater layoutInflater;
        Holder holder= new Holder();
        if(convertView==null){
            layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= layoutInflater.inflate(R.layout.paymentitem, null);
            holder.pid= convertView.findViewById(R.id.payment_id);
            holder.oname= convertView.findViewById(R.id.payment_owner);
            holder.rname= convertView.findViewById(R.id.payment_receiver);
            holder.amount= convertView.findViewById(R.id.payment_amount);
            holder.date= convertView.findViewById(R.id.payment_date);
            convertView.setTag(holder);

        }else holder= (Holder) convertView.getTag();

        holder.pid.setText(PAYMENT_ID.get(position)+"");
        holder.oname.setText(PAYMENT_OWNER.get(position));
        holder.rname.setText(PAYMENT_RECEIVER.get(position));
        holder.amount.setText(PAYMENT_AMOUNT.get(position).toString());
        holder.date.setText(PAYMENT_DATE.get(position));

        return convertView;
    }

    public class Holder{
        TextView pid, oname, rname, date, amount;

    }
}
