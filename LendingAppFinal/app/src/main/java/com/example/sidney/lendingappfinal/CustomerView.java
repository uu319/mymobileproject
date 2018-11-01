package com.example.sidney.lendingappfinal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sidney on 5/7/2018.
 */

public class CustomerView extends AppCompatActivity {
    Button viewPayments, viewAttendance;
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    TextView logout;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerview);
        sqLiteHelper= new SQLiteHelper(this);
        sqLiteDatabase= sqLiteHelper.getWritableDatabase();
        Bundle b= getIntent().getExtras();
        int customer_id=0;
        viewAttendance= findViewById(R.id.viewAttendance);
        viewPayments= findViewById(R.id.viewPayments);
        logout= findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CustomerView.this, LoginForm.class);
                startActivity(intent);
            }
        });
        viewPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> PAYMENT_ID= new ArrayList<Integer>();
                ArrayList<String>  PAYMENT_OWNER= new ArrayList<String>();
                ArrayList<Integer>  PAYMENT_RECEIVER= new ArrayList<Integer>();
                ArrayList<String>  PAYMENT_DATE= new ArrayList<String>();
                ArrayList<Double>  PAYMENT_AMOUNT= new ArrayList<Double>();
                ArrayList<String> PAYMENT_RECEIVER_NAME= new ArrayList<String>();
                PAYMENT_ID.clear();
                PAYMENT_OWNER.clear();
                PAYMENT_RECEIVER.clear();
                PAYMENT_DATE.clear();
                PAYMENT_AMOUNT.clear();
                PAYMENT_RECEIVER_NAME.clear();
                String fname, lname, mname, fullname="";
                String sfname, slname, smname, sfullname="";

                Bundle b= getIntent().getExtras();
                int customer_id=0;
                if(b!=null)
                customer_id= b.getInt("customerID");
                cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_PERSON+" WHERE "+SQLiteHelper.COLUMN_P_ID
                        +" = "+customer_id, null);
                if(cursor.moveToFirst()){
                    fname= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME));
                    mname= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME));
                    lname= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME));
                    fullname= fname+" "+mname+" "+lname;
                }
                cursor.close();
                cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_PAYMENT+
                        " WHERE "+SQLiteHelper.COLUMN_PAYMENT_lID +" = "+customer_id, null);
                if(cursor.moveToFirst()){
                    do{
                   PAYMENT_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_PAYMENT_ID)));
                   PAYMENT_OWNER.add(fullname);
                   PAYMENT_RECEIVER.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_PAYMENT_SID)));
                   PAYMENT_DATE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_PAYMENT_DATE)));
                   PAYMENT_AMOUNT.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_PAYMENT_AMOUNT)));
                    }while(cursor.moveToNext());
                }cursor.close();
                int num=0;
                if(PAYMENT_ID.size()>0){
                do {
                    cursor= sqLiteDatabase.rawQuery("SELECT "+SQLiteHelper.COLUMN_P_FNAME+", "+SQLiteHelper.COLUMN_P_MNAME
                            +", "+SQLiteHelper.COLUMN_P_LNAME+" FROM "+SQLiteHelper.TABLE_PERSON+" WHERE "+SQLiteHelper.COLUMN_P_ID+" = "
                            +PAYMENT_RECEIVER.get(num), null);
                    cursor.moveToFirst();
                    sfname= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME));
                    smname= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME));
                    slname= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME));
                    sfullname= sfname+" "+smname+" "+slname;
                    PAYMENT_RECEIVER_NAME.add(sfullname);
                             num++;
                } while ( num < PAYMENT_RECEIVER.size());
                AlertDialog.Builder builder= new AlertDialog.Builder(CustomerView.this);
                View view= getLayoutInflater().inflate(R.layout.listview, null);
                builder.setView(view);
                ListView listView= view.findViewById(R.id.listview1);
                PaymentListAdapter adapter= new PaymentListAdapter(CustomerView.this,PAYMENT_ID, PAYMENT_OWNER, PAYMENT_RECEIVER_NAME,
                        PAYMENT_DATE, PAYMENT_AMOUNT );
                listView.setAdapter(adapter);
                AlertDialog dialog= builder.create();
                dialog.show();}
                else Toast.makeText(CustomerView.this, "You have no payment records yet", Toast.LENGTH_SHORT).show();
            }

        });
        viewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b= getIntent().getExtras();
                int customer_id=0;
                if(b!=null)
                    customer_id= b.getInt("customerID");
                Intent intent = new Intent(CustomerView.this, AttendanceDisplay.class);
                intent.putExtra("customer_id",customer_id);
                startActivity(intent);
            }
        });
    }
}
