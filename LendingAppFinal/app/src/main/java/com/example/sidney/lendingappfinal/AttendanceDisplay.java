package com.example.sidney.lendingappfinal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Sidney on 5/24/2018.
 */

public class AttendanceDisplay extends AppCompatActivity {
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    AttendanceListAdapter adapter;
    ArrayList<Integer> ATT_ID;
    ArrayList<String> ATT_DATE;
    ArrayList<String> ATT_STATUS;
    ArrayList<Double> ATT_AMOUNTEXPECT;
    ArrayList<Integer> PAYMENT_LID;
    ArrayList<Integer> PAYMENT_ID;
    ArrayList<Double> ATT_PAID2;

    ListView listView;
    TextView att_id, att_date, att_amountexpec, att_status, att_owner, att_paid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        sqLiteHelper = new SQLiteHelper(this);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        ATT_ID = new ArrayList<Integer>();
        ATT_DATE = new ArrayList<String>();
        ATT_STATUS = new ArrayList<String>();
        ATT_AMOUNTEXPECT = new ArrayList<Double>();
        PAYMENT_LID = new ArrayList<Integer>();
        PAYMENT_ID = new ArrayList<Integer>();
        ATT_PAID2 = new ArrayList<Double>();
        int customer_num = 0;
        Bundle b = getIntent().getExtras();
        if (b != null)
            customer_num = b.getInt("customer_id");
        listView = findViewById(R.id.listview1);
        att_id = findViewById(R.id.att_id);
        att_date = findViewById(R.id.att_date);
        att_amountexpec = findViewById(R.id.amountExpected);
        att_status = findViewById(R.id.att_status);
        att_paid = findViewById(R.id.att_paid);
        PopulateArrays();
        adapter = new AttendanceListAdapter(this, ATT_ID, ATT_DATE, ATT_AMOUNTEXPECT, ATT_STATUS, ATT_PAID2);
        listView.setAdapter(adapter);
    }

    public void PopulateArrays() {
        ATT_ID.clear();
        ATT_STATUS.clear();
        ATT_DATE.clear();
        ATT_AMOUNTEXPECT.clear();
        ATT_PAID2.clear();
        PAYMENT_ID.clear();
        int customer_num = 0;
        Bundle b = getIntent().getExtras();
        if (b != null)
            customer_num = b.getInt("customer_id");
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_PAYMENT
                + " WHERE " + SQLiteHelper.COLUMN_PAYMENT_lID + " = " + customer_num, null);
        if (cursor.moveToFirst()) {
            do {
                PAYMENT_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_PAYMENT_ID)));
                ATT_PAID2.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_PAYMENT_AMOUNT)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        int counter = 0;
        do {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_ATTENDANCE + " WHERE "
                    + SQLiteHelper.COLUMN_ATTENDANCE_ID
                    + " = " + PAYMENT_ID.get(counter), null);
            if (cursor.moveToFirst()) {
                do {
                    ATT_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_ATTENDANCE_ID)));
                    ATT_STATUS.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_ATTENDANCE_STATUS)));
                    ATT_DATE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_ATTENDANCE_DATE)));
                    ATT_AMOUNTEXPECT.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_ATTENDANCE_AMOUNT_EXPEC)));
                } while (cursor.moveToNext());
                counter++;
            }
        } while (counter < PAYMENT_ID.size());
        cursor.close();
    }
}
