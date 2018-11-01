package com.example.sidney.lendingappfinal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sidney on 5/27/2018.
 */

public class ReportDisplay extends AppCompatActivity {
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ReportListAdapter adapter;
    ListView listView;
    TextView r_id,r_date, r_action, r_action_id;
    ArrayList<Integer> R_ID;
    ArrayList<Integer> R_ACTION_ID;
    ArrayList<String> R_DATE;
    ArrayList<String> R_ACTION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        R_ID= new ArrayList<Integer>();
        R_ACTION_ID= new ArrayList<Integer>();
        R_ACTION= new ArrayList<String>();
        R_DATE= new ArrayList<String>();
        sqLiteHelper= new SQLiteHelper(this);
        sqLiteDatabase= sqLiteHelper.getWritableDatabase();
        listView= findViewById(R.id.listview1);
        r_id= findViewById(R.id.r_id);
        r_date= findViewById(R.id.r_action);
        r_action= findViewById(R.id.r_action);
        r_action_id= findViewById(R.id.r_action_id);
        PopulateReportArray();
        adapter= new ReportListAdapter(this, R_ID,R_ACTION_ID, R_DATE, R_ACTION);
        listView.setAdapter(adapter);
    }
    public void PopulateReportArray(){
        R_ID.clear();
        R_DATE.clear();
        R_ACTION.clear();
        R_ACTION_ID.clear();

        cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_REPORT, null);
        if(cursor.moveToFirst()){
            do {
                R_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_REPORT_ID)));
                R_ACTION_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_REPORT_ACTION_ID)));
                R_DATE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_REPORT_DATE)));
                R_ACTION.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_REPORT_ACTION)));
            }while (cursor.moveToNext());
        }cursor.close();
    }
}
