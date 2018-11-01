package com.example.sidney.lendingappfinal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Sidney on 5/2/2018.
 */

public class StaffView extends AppCompatActivity {
    Button addPerson, addLoan, displayPerson, displayLoan, addPayment, displayPayment;
    TextView logout, test;
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ArrayList<Integer> LOAN_PERSON_ID;
    ArrayList<String> LOAN_DATE;
    ArrayList<String> LOAN_DUE;
    ArrayList<Double> LOAN_BALANCE;
    ArrayList<String> LNAME, MNAME, FNAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffview);
        addPerson= findViewById(R.id.addPerson);
        addLoan= findViewById(R.id.addLoan);
        displayPerson=findViewById(R.id.displayPerson);
        displayLoan=findViewById(R.id.displayLoan);
        addPayment=findViewById(R.id.addPayment);
        displayPayment= findViewById(R.id.displayPayment);
        logout= findViewById(R.id.logout);
        test= findViewById(R.id.test);
        sqLiteHelper= new SQLiteHelper(this);
        sqLiteDatabase= sqLiteHelper.getWritableDatabase();
        LOAN_PERSON_ID= new ArrayList<Integer>();
        LOAN_DATE= new ArrayList<String>();
        LOAN_DUE= new ArrayList<String>();
        LOAN_BALANCE= new ArrayList<Double>();
        MNAME= new ArrayList<String>();
        FNAME= new ArrayList<String>();
        LNAME= new ArrayList<String>();
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOAN_PERSON_ID.clear();
                LOAN_DATE.clear();
                LOAN_DUE.clear();
                LOAN_BALANCE.clear();
                String cd_date, cd_due;
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
               Calendar today = Calendar.getInstance();
                today.add(Calendar.DATE, -1);
               String dateToday = sdf.format(today.getTime());
               cursor= sqLiteDatabase.rawQuery("SELECT * FROM "
                       +SQLiteHelper.TABLE_LOAN+ " WHERE "+SQLiteHelper.COLUMN_L_DUE+" = '"+dateToday+"' AND "
                       +SQLiteHelper.COLUMN_L_STATUS+" = 'Active'", null);
               if(cursor.moveToFirst()){
                   do{
                       double balance= cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_BALANCE));
                       int p_id= cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_PID));
                       cd_date= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_DATE));
                       cd_due= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_DUE));
                       sqLiteHelper.insertIntoCDReport(sqLiteDatabase, p_id, cd_date,
                               cd_due, balance, "Unsettled");
                       String UPDATE_PERSON_CREDIT= "UPDATE "+SQLiteHelper.TABLE_PERSON+" SET "+SQLiteHelper.COLUMN_P_CREDIT
                               +" = "+balance+", "+SQLiteHelper.COLUMN_P_STATUS+" = 'Inactive'"+ " WHERE "+SQLiteHelper.COLUMN_P_ID+" = "+p_id;
                       sqLiteDatabase.execSQL(UPDATE_PERSON_CREDIT);
                   }while (cursor.moveToNext());

               }cursor.close();

               String UPDATE_LOAN_STATUS_BY_DATE="UPDATE "+SQLiteHelper.TABLE_LOAN+" SET "+SQLiteHelper.COLUMN_L_STATUS+" = 'Inactive' WHERE "
                      +SQLiteHelper.COLUMN_L_DUE +" = "+"'"+dateToday+"' AND "+SQLiteHelper.COLUMN_L_STATUS+" = 'Active'";
               Toast.makeText(StaffView.this, dateToday, Toast.LENGTH_SHORT).show();

               sqLiteDatabase.execSQL(UPDATE_LOAN_STATUS_BY_DATE);

            }
        });


        displayPayment.setOnClickListener( new  View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                Intent intent= new Intent(StaffView.this, PaymentDisplay.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffView.this, LoginForm.class);
                startActivity(intent);
            }
        });
        addPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(StaffView.this, AddPayment.class);
                startActivity(intent);
            }
        });

        displayLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(StaffView.this, LoanDisplay.class);
                startActivity(intent);
            }
        });
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffView.this, AddCustomerForm.class);
                String type="Customer";
                int test=1;
                intent.putExtra("type", type);
                intent.putExtra("test", test);
                startActivity(intent);
            }
        });

        addLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffView.this, AddLoan.class);
                startActivity(intent);
            }
        });

        displayPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffView.this, PersonDisplay.class);
                startActivity(intent);
            }
        });



    }

}
