package com.example.sidney.lendingappfinal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Sidney on 5/10/2018.
 */

public class PaymentDisplay extends AppCompatActivity {
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    PaymentListAdapter adapter;
    ListView listView;
    TextView payment_id, owner, receiver, amount, date;
    Cursor cursor;
    ArrayList<Integer> PAYMENT_ID;
    ArrayList<String> PAYMENT_OWNER, PAYMENT_RECEIVER, PAYMENT_DATE;
    ArrayList<Double> PAYMENT_AMOUNT;
    ArrayList<Integer> LOAN_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        listView= findViewById(R.id.listview1);
        PAYMENT_ID= new ArrayList<Integer>();
        PAYMENT_OWNER= new ArrayList<String>();
        LOAN_ID= new ArrayList<Integer>();
        PAYMENT_RECEIVER= new ArrayList<String>();
        PAYMENT_DATE= new ArrayList<String>();
        PAYMENT_AMOUNT= new ArrayList<Double>();
        sqLiteHelper= new SQLiteHelper(this);
        sqLiteDatabase= sqLiteHelper.getWritableDatabase();
        PopulateArrays();
        PopulateOwnerNameArrays();
        PopulateStaffArray();
        adapter= new PaymentListAdapter(this, PAYMENT_ID, PAYMENT_OWNER, PAYMENT_RECEIVER, PAYMENT_DATE, PAYMENT_AMOUNT);
        listView.setAdapter(adapter);


    }
    public void PopulateArrays(){
        String queryHolder= "SELECT * FROM "+SQLiteHelper.TABLE_PAYMENT;
        cursor= sqLiteDatabase.rawQuery(queryHolder, null);
        PAYMENT_ID.clear();
        PAYMENT_AMOUNT.clear();
        PAYMENT_DATE.clear();
        if(cursor.moveToFirst()){
            do {
                PAYMENT_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_PAYMENT_ID)));
                PAYMENT_AMOUNT.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_PAYMENT_AMOUNT)));
                PAYMENT_DATE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_PAYMENT_DATE)));
            }while (cursor.moveToNext());
        }
        cursor.close();

    }
    public void PopulateOwnerNameArrays() {
        String fname, mname, lname, fullname;
      cursor = sqLiteDatabase.rawQuery("SELECT " + SQLiteHelper.COLUMN_P_FNAME + ", " + SQLiteHelper.COLUMN_P_MNAME
                + ", " + SQLiteHelper.COLUMN_P_LNAME + " FROM " + SQLiteHelper.TABLE_PERSON + ", " + SQLiteHelper.TABLE_PAYMENT
                + " WHERE " + SQLiteHelper.COLUMN_PAYMENT_lID + " = " + SQLiteHelper.COLUMN_P_ID, null);
      /*  cursor=sqLiteDatabase.rawQuery("SELECT "+SQLiteHelper.COLUMN_P_FNAME+", "+SQLiteHelper.COLUMN_P_MNAME+", "
                                            +SQLiteHelper.COLUMN_P_LNAME+" FROM "+SQLiteHelper.TABLE_PERSON+", "
                                            + SQLiteHelper.TABLE_LOAN+" ON "+SQLiteHelper.COLUMN_P_ID+" = "
                                            +"(SELECT "+SQLiteHelper.COLUMN_L_PID+" FROM "+SQLiteHelper.TABLE_LOAN+", "
                                            +SQLiteHelper.TABLE_PAYMENT+" WHERE "+SQLiteHelper.COLUMN_L_ID+" = "
                                            +SQLiteHelper.COLUMN_PAYMENT_lID+")", null);
       cursor= sqLiteDatabase.rawQuery("SELECT "+SQLiteHelper.COLUMN_L_ID+" FROM "+SQLiteHelper.TABLE_LOAN+", "
                                       +SQLiteHelper.TABLE_PAYMENT +" WHERE "+SQLiteHelper.COLUMN_L_ID+" = "
                                       +SQLiteHelper.COLUMN_PAYMENT_lID, null);*/
        PAYMENT_OWNER.clear();
            if (cursor.moveToFirst()) {
                do{
                    fname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME));
                    mname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME));
                    lname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME));
                    PAYMENT_OWNER.add(fname+" "+lname);
                }while (cursor.moveToNext());
            }
        cursor.close();
    }
    public void PopulateStaffArray(){

        String sfname, smname, slname, sfullname;
        cursor= sqLiteDatabase.rawQuery("SELECT "+SQLiteHelper.COLUMN_P_FNAME+", "+SQLiteHelper.COLUMN_P_MNAME
                +", "+SQLiteHelper.COLUMN_P_LNAME+" FROM "+SQLiteHelper.TABLE_PERSON+", "+SQLiteHelper.TABLE_PAYMENT
                +" WHERE "+SQLiteHelper.COLUMN_PAYMENT_SID+" = "+SQLiteHelper.COLUMN_P_ID, null);
        PAYMENT_RECEIVER.clear();
        if(cursor.moveToFirst()){
            do{

                sfname= cursor   .getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME));
                smname= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME));
                slname= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME));
                sfullname= sfname+" "+slname;
                PAYMENT_RECEIVER.add(sfullname);

            }while (cursor.moveToNext());
            cursor.close();


        }
    }
    }