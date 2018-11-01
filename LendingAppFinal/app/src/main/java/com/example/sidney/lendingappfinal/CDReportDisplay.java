package com.example.sidney.lendingappfinal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLInput;
import java.util.ArrayList;

/**
 * Created by Sidney on 5/15/2018.
 */

public class CDReportDisplay extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    ArrayList<Integer> CD_REPORT_ID;
    ArrayList<String> CD_STATUS;
    ArrayList<Integer> CD_PERSON_ID;
    ArrayList<String> CD_LOAN_DUE;
    ArrayList<String> CD_LOAN_DATE;
    ArrayList<Double> CD_LOAN_BALANCE;
    ArrayList<String> CD_PERSON_NAME;
    TextView cd_id, cd_pid, cd_date, cd_due, cd_balance;

    ListView listView;
    CDReportListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        CD_REPORT_ID= new ArrayList<Integer>();
        CD_STATUS= new ArrayList<String>();
        CD_PERSON_ID= new ArrayList<Integer>();
        CD_LOAN_DATE= new ArrayList<String>();
        CD_LOAN_DUE= new ArrayList<String>();
        CD_LOAN_BALANCE= new ArrayList<Double>();
        CD_PERSON_NAME= new ArrayList<String>();
        cd_id= findViewById(R.id.cd_reportID);
        cd_pid= findViewById(R.id.cd_customerName);
        cd_date= findViewById(R.id.cd_loanDate);
        cd_due= findViewById(R.id.cd_loanDue);
        cd_balance= findViewById(R.id.cd_loanBalance);
        listView= findViewById(R.id.listview1);
        sqLiteHelper= new SQLiteHelper(this);
        sqLiteDatabase= sqLiteHelper.getWritableDatabase();
        PopulateCDReport();
        adapter= new CDReportListAdapter(this,CD_REPORT_ID, CD_PERSON_NAME, CD_LOAN_DATE, CD_LOAN_DUE, CD_LOAN_BALANCE, CD_STATUS);
        listView.setAdapter(adapter);
        Toast.makeText(CDReportDisplay.this, CD_REPORT_ID.size()+"", Toast.LENGTH_LONG).show();
    }

    public void PopulateCDReport(){
        CD_PERSON_ID.clear();
        CD_LOAN_BALANCE.clear();
        CD_LOAN_DATE.clear();
        CD_LOAN_DUE.clear();
        CD_PERSON_NAME.clear();
        String fname, lname, mname, fullname;

       cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_CDREPORT+", "+SQLiteHelper.TABLE_PERSON
        +" WHERE "+SQLiteHelper.COLUMN_P_ID+" = "+SQLiteHelper.COLUMN_CD_PERSON_ID, null);
       if(cursor.moveToFirst()){
           do {
               fname= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME));
               mname= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME));
               lname= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME));
               fullname= fname+" "+mname+" "+lname;
               CD_PERSON_NAME.add(fullname);
               CD_REPORT_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_CD_REPORT_ID)));
               CD_PERSON_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_CD_PERSON_ID)));
               CD_LOAN_BALANCE.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_CD_LOAN_BALANCE)));
               CD_LOAN_DATE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_CD_LOAN_DATE)));
               CD_LOAN_DUE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_CD_LOAN_DUE)));
               CD_STATUS.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_CD_STATUS)));
           }while (cursor.moveToNext());
       }cursor.close();
    }


}
