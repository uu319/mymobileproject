package com.example.sidney.lendingappfinal;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Sidney on 5/3/2018.
 */

public class LoanDisplay extends AppCompatActivity {
    LoanListAdapter adapter;
    ListView listView1;
    ArrayList<Integer> LOAN_P_ID; String p_fname, p_mname, p_lname; ArrayList<String> P_NAME;
    ArrayList<Integer> LOAN_S_ID; String s_fname, s_mname, s_lname; ArrayList<String> S_NAME;
    ArrayList<Integer> LOAN_R_ID; String r_fname, r_mname, r_lname; ArrayList<String> R_NAME;
    ArrayList<Integer> LOAN_ID;

    ArrayList<String> L_LDATE, L_DDATE, L_STATUS, L_TYPE;
    ArrayList<Double> L_AMOUNT, L_INTRATE, L_INTAMOUNT, L_TOTAL, L_BALANCE;


    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        sqLiteHelper= new SQLiteHelper(this);
        sqLiteDatabase=sqLiteHelper.getWritableDatabase();

        L_LDATE= new ArrayList<String>();  L_DDATE= new ArrayList<String>();
        L_STATUS= new ArrayList<String>();  L_TYPE= new ArrayList<String>();
        L_AMOUNT= new ArrayList<Double>(); L_INTRATE= new ArrayList<Double>();
        L_INTAMOUNT= new ArrayList<Double>(); L_TOTAL= new ArrayList<Double>();
        L_BALANCE= new ArrayList<Double>();
        LOAN_P_ID= new ArrayList<Integer>(); P_NAME= new ArrayList<String>();
        LOAN_S_ID= new ArrayList<Integer>(); S_NAME= new ArrayList<String>();
        LOAN_R_ID= new ArrayList<Integer>(); R_NAME= new ArrayList<String>();
        LOAN_ID= new ArrayList<Integer>();
        listView1= findViewById(R.id.listview1);
        PopulateLoadID();
        PopulatePersonName();
        PopulateStaffName();
        PopulateRefName();
        PopulateOtherArrays();
        adapter= new LoanListAdapter(this, LOAN_ID, P_NAME, S_NAME, R_NAME, L_BALANCE, L_STATUS);
        listView1.setAdapter(adapter);

        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(LoanDisplay.this, "Test", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(LoanDisplay.this);
                View view3= getLayoutInflater().inflate(R.layout.displayloan, null);
                builder.setView(view3);
                TextView l_id,l_personName, l_staffName, l_refName, l_ldate, l_ddate, l_amount, l_intRate, l_intAmount, l_total,
                         l_type, l_balance, l_status;
                l_id= view3.findViewById(R.id.l_id); l_personName= view3.findViewById(R.id.l_personName);
                l_staffName= view3.findViewById(R.id.l_staffName); l_refName= view3.findViewById(R.id.l_refName);
                l_ldate= view3.findViewById(R.id.l_ldate); l_ddate= view3.findViewById(R.id.l_ddate);
                l_amount= view3.findViewById(R.id.l_amount); l_intRate= view3.findViewById(R.id.l_intRate);
                l_intAmount= view3.findViewById(R.id.l_intAmount); l_total= view3.findViewById(R.id.l_total);
                l_type= view3.findViewById(R.id.l_type) ;l_balance= view3.findViewById(R.id.l_balance);
                l_status= view3.findViewById(R.id.l_status);

                l_id.setText(LOAN_ID.get(position)+"");
                l_personName.setText(P_NAME.get(position));
                l_staffName.setText(S_NAME.get(position));
                l_refName.setText(R_NAME.get(position));
                l_ldate.setText(L_LDATE.get(position));
                l_ddate.setText(L_DDATE.get(position));
                l_amount.setText(L_AMOUNT.get(position).toString());
                l_intRate.setText(L_INTRATE.get(position).toString());
                l_intAmount.setText(L_INTAMOUNT.get(position).toString());
                l_total.setText(L_TOTAL.get(position).toString());
                l_type.setText(L_TYPE.get(position));
                l_balance.setText(L_BALANCE.get(position).toString());
                l_status.setText(L_STATUS.get(position));

                Dialog dialog= builder.create();
                dialog.show();

            }
        });

    }
    public void PopulateLoadID(){
        LOAN_ID.clear();
        cursor=sqLiteDatabase.rawQuery("SELECT "+SQLiteHelper.COLUMN_L_ID+" FROM "+SQLiteHelper.TABLE_LOAN, null);
        if(cursor.moveToFirst()){
            do{
                LOAN_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_ID)));
            }while (cursor.moveToNext());
        }


    }
    public void PopulatePersonName(){
        P_NAME.clear();
        cursor= sqLiteDatabase.rawQuery("SELECT "+SQLiteHelper.COLUMN_P_FNAME+", " +SQLiteHelper.COLUMN_P_MNAME+", "
                +SQLiteHelper.COLUMN_P_LNAME+ " FROM "+SQLiteHelper.TABLE_PERSON+", "+ SQLiteHelper.TABLE_LOAN
                + " WHERE "+SQLiteHelper.COLUMN_P_ID+" = "+SQLiteHelper.COLUMN_L_PID, null);
        if (cursor.moveToFirst()){
            do{
                p_fname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME));
                p_mname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME));
                p_lname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME));
                P_NAME.add(p_fname + " " + p_mname + " " + p_lname);

            }while(cursor.moveToNext());
            }
        }
    public void PopulateStaffName() {
        S_NAME.clear();
        cursor= sqLiteDatabase.rawQuery("SELECT "+SQLiteHelper.COLUMN_P_FNAME+", " +SQLiteHelper.COLUMN_P_MNAME+", "
                +SQLiteHelper.COLUMN_P_LNAME+ " FROM "+SQLiteHelper.TABLE_PERSON+", "+ SQLiteHelper.TABLE_LOAN
                + " WHERE "+SQLiteHelper.COLUMN_P_ID+" = "+SQLiteHelper.COLUMN_L_SID, null);
        if (cursor.moveToFirst()){
            do{
                s_fname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME));
                s_mname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME));
                s_lname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME));
                S_NAME.add(s_fname + " " + s_mname + " " + s_lname);

            }while(cursor.moveToNext());
    }
    }
    public void PopulateRefName(){
        R_NAME.clear();
      cursor= sqLiteDatabase.rawQuery("SELECT "+SQLiteHelper.COLUMN_P_FNAME+", " +SQLiteHelper.COLUMN_P_MNAME+", "
                                           +SQLiteHelper.COLUMN_P_LNAME+ " FROM "+SQLiteHelper.TABLE_PERSON+", "+ SQLiteHelper.TABLE_LOAN
                                           + " WHERE "+SQLiteHelper.COLUMN_P_ID+" = "+SQLiteHelper.COLUMN_L_RID, null);
      if (cursor.moveToFirst()){
          do{
              r_fname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME));
              r_mname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME));
              r_lname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME));
              R_NAME.add(r_fname + " " + r_mname + " " + r_lname);

          }while(cursor.moveToNext());

      }
    }

    public void PopulateOtherArrays(){
            cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_LOAN, null);
            L_DDATE.clear();
            L_LDATE.clear();
            L_STATUS.clear();
            L_TYPE.clear();
            L_AMOUNT.clear();
            L_INTRATE.clear();
            L_INTAMOUNT.clear();
            L_TOTAL.clear();
            L_BALANCE.clear();
            if(cursor.moveToFirst()){
                do {
                    L_LDATE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_DATE)));
                    L_DDATE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_DUE)));
                    L_STATUS.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_STATUS)));
                    L_TYPE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_TYPE)));
                    L_AMOUNT.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_AMOUNT)));
                    L_INTRATE.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_INTRATE)));
                    L_INTAMOUNT.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_INTAMOUNT)));
                    L_TOTAL.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_TOTAL)));
                    L_BALANCE.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_BALANCE)));
                }while (cursor.moveToNext());

            }

    }



}
