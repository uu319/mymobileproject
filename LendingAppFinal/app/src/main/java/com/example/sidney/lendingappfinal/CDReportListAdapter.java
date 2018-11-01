package com.example.sidney.lendingappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sidney on 5/15/2018.
 */

public class CDReportListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> CD_REPORT_ID;
    ArrayList<String> CD_PERSON_ID;
    ArrayList<String> CD_LOAN_DATE;
    ArrayList<String> CD_LOAN_DUE;
    ArrayList<Double> CD_LOAN_BALANCE;
    ArrayList<String> CD_STATUS;
    public CDReportListAdapter(Context context, ArrayList<Integer> CD_REPORT_ID, ArrayList<String> CD_PERSON_ID, ArrayList<String> CD_LOAN_DATE,
                               ArrayList<String> CD_LOAN_DUE, ArrayList<Double> CD_LOAN_BALANCE, ArrayList<String> CD_STATUS) {
        this.context = context;
        this.CD_REPORT_ID = CD_REPORT_ID;
        this.CD_PERSON_ID = CD_PERSON_ID;
        this.CD_LOAN_DATE = CD_LOAN_DATE;
        this.CD_LOAN_DUE = CD_LOAN_DUE;
        this.CD_LOAN_BALANCE = CD_LOAN_BALANCE;
        this.CD_STATUS= CD_STATUS;
    }


    @Override
    public int getCount() {
        return CD_REPORT_ID.size();
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
            convertView= inflater.inflate(R.layout.cdreportitem, null);

            holder.cd_reportID= convertView.findViewById(R.id.cd_reportID);
            holder.cd_personID= convertView.findViewById(R.id.cd_customerName);
            holder.cd_loanDate= convertView.findViewById(R.id.cd_loanDate);
            holder.cd_loanDue= convertView.findViewById(R.id.cd_loanDue);
            holder.cd_loanBalance= convertView.findViewById(R.id.cd_loanBalance);
            holder.cd_status= convertView.findViewById(R.id.cd_status);

            convertView.setTag(holder);
        }else holder= (Holder) convertView.getTag();

        holder.cd_reportID.setText(CD_REPORT_ID.get(position).toString());
        holder.cd_personID.setText(CD_PERSON_ID.get(position));
        holder.cd_loanDate.setText(CD_LOAN_DATE.get(position));
        holder.cd_loanDue.setText(CD_LOAN_DUE.get(position));
        holder.cd_loanBalance.setText(CD_LOAN_BALANCE.get(position).toString());
        holder.cd_status.setText(CD_STATUS.get(position));



        return convertView;
    }

    public class Holder{
        TextView cd_reportID, cd_personID, cd_loanDate, cd_loanDue, cd_loanBalance, cd_status;

    }
}
