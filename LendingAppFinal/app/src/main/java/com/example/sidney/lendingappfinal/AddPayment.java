package com.example.sidney.lendingappfinal;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sidney on 5/5/2018.
 */

public class AddPayment extends AppCompatActivity {
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    EditText paymentdate, paymentamount, paymentowner, paymentreceiver;
    Button savePayment, cancelPayment;
    /*-------------------Paymenr Owner Listview----------------------------*/
    ArrayList<Integer> LOAN_P_ID; ArrayList<String>P_NAME; String p_fname, p_mname, p_lname;
    ArrayList<Integer> LOAN_S_ID; ArrayList<String>S_NAME; String s_fname, s_mname, s_lname;
    ArrayList<Integer> LOAN_R_ID; ArrayList<String>R_NAME; String r_fname, r_mname, r_lname;
    ArrayList<Integer> LOAN_ID;
    /*---------------------------Loan Owner Listview----------------------------*/
    ArrayList<String>L_DDATE, L_LDATE, L_STATUS, L_TYPE;
    ArrayList<Double>L_AMOUNT, L_INTRATE, L_INTAMOUNT, L_TOTAL, L_BALANCE;

    /*---------------------------Payment Receiver Listview----------------------------*/
    ArrayList<String>  PERSON_FNAME, PERSON_MNAME, PERSON_LNAME, PERSON_TYPE, PERSON_STATUS;
    ArrayList<Double> PERSON_CREDIT;
    ArrayList<Integer> PERSON_ID;
    /*---------------------------Payment Receiver Listview----------------------------*/

    /*---------------------------Owner and Receiver ID----------------------------*/
     int p_idHolder, staff_idHolder, loan_ID;
     PersonListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpayment);
        sqLiteHelper= new SQLiteHelper(this);
        sqLiteDatabase=sqLiteHelper.getWritableDatabase();
        paymentdate= findViewById(R.id.paymentdate);
        paymentamount= findViewById(R.id.paymentamount);
        paymentowner=findViewById(R.id.paymentowner);
        paymentreceiver=findViewById(R.id.paymentreceiver);
        savePayment= findViewById(R.id.paymentConfirm);
        cancelPayment= findViewById(R.id.paymentCancel);
        LOAN_P_ID= new ArrayList<Integer>(); LOAN_R_ID= new ArrayList<Integer>(); LOAN_S_ID= new ArrayList<Integer>();
        P_NAME= new ArrayList<String>();  R_NAME= new ArrayList<String>();  S_NAME= new ArrayList<String>();
        L_LDATE= new ArrayList<String>(); L_DDATE= new ArrayList<String>(); L_STATUS= new ArrayList<String>();
        L_TYPE= new ArrayList<String>();
        L_AMOUNT= new ArrayList<Double>(); L_INTRATE= new ArrayList<Double>(); L_INTAMOUNT= new ArrayList<Double>();
        L_TOTAL= new ArrayList<Double>(); L_BALANCE= new ArrayList<Double>();
        LOAN_ID= new ArrayList<Integer>();

        PERSON_ID= new ArrayList<Integer>(); PERSON_CREDIT= new ArrayList<Double>();
        PERSON_FNAME= new ArrayList<String>();PERSON_MNAME= new ArrayList<String>();
        PERSON_LNAME= new ArrayList<String>();PERSON_TYPE= new ArrayList<String>();
        PERSON_STATUS= new ArrayList<String>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar today = Calendar.getInstance();
        final String paymentDate = sdf.format(today.getTime());
        paymentdate.setText(paymentDate);
        paymentdate.setEnabled(false);

        paymentowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopulateLoadID();
                PopulatePersonName();
                PopulateStaffName();
                PopulateRefName();
                PopulateOtherArrays();
                AlertDialog.Builder builder= new AlertDialog.Builder(AddPayment.this);
                View view= getLayoutInflater().inflate(R.layout.listview, null);
                ListView listView3= view.findViewById(R.id.listview1);
                builder.setView(view);
                LoanListAdapter adapter= new LoanListAdapter(AddPayment.this, LOAN_ID, P_NAME, S_NAME, R_NAME, L_BALANCE, L_STATUS);
                listView3.setAdapter(adapter);

                final AlertDialog dialog=builder.create();
                dialog.show();
                listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        p_idHolder=LOAN_P_ID.get(position);
                        loan_ID= LOAN_ID.get(position);

                        paymentowner.setText(P_NAME.get(position));
                        Toast.makeText(AddPayment.this, p_idHolder+"",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

            }
        });
        paymentreceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AlertDialog.Builder builder= new AlertDialog.Builder(AddPayment.this);
               View view= getLayoutInflater().inflate(R.layout.listview, null);
               ListView listView4= view.findViewById(R.id.listview1);
               PopulatePersonArray();
               adapter= new PersonListAdapter(PERSON_ID, PERSON_FNAME, PERSON_MNAME, PERSON_LNAME, PERSON_TYPE, PERSON_STATUS,
                       PERSON_CREDIT,AddPayment.this);
               listView4.setAdapter(adapter);
               builder.setView(view);
               final AlertDialog dialog=builder.create();
               dialog.show();

               listView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       staff_idHolder= PERSON_ID.get(position);
                       paymentreceiver.setText(PERSON_FNAME.get(position)+" "+PERSON_MNAME.get(position)
                               +" "+PERSON_LNAME.get(position));
                       Toast.makeText(AddPayment.this, staff_idHolder+"",Toast.LENGTH_LONG).show();
                       dialog.dismiss();
                   }
               });


            }
        });
        savePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteHelper.insertIntoPayment(sqLiteDatabase, paymentdate.getText().toString(),
                        Double.parseDouble(paymentamount.getText().toString()), p_idHolder, staff_idHolder);
                double balance;
                String queryHolder;
                String UPDATE_LOAN_STATUS_BY_BALANCE;
                String UPDATE_PERSON_STATUS_BY_BALANCE;
                int id;
                cursor= sqLiteDatabase.rawQuery("SElECT * FROM "+SQLiteHelper.TABLE_LOAN
                        +" WHERE "+SQLiteHelper.COLUMN_L_ID+" = "+ loan_ID, null);
                if(cursor.moveToFirst()){
                    id= cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_ID));
                    balance= cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_BALANCE));
                    balance=balance-Double.parseDouble(paymentamount.getText().toString());
                    queryHolder="UPDATE "+SQLiteHelper.TABLE_LOAN+" SET "+SQLiteHelper.COLUMN_L_BALANCE+
                            " = "+balance +" WHERE "+SQLiteHelper.COLUMN_L_ID+" = "+id;
                    sqLiteDatabase.execSQL(queryHolder);

                    UPDATE_LOAN_STATUS_BY_BALANCE= "UPDATE "+SQLiteHelper.TABLE_LOAN+" SET "+SQLiteHelper.COLUMN_L_STATUS+" = 'Inactive'"
                            +" WHERE "+SQLiteHelper.COLUMN_L_BALANCE+" = "+0 +" AND "+SQLiteHelper.COLUMN_L_STATUS+" = 'Active'";

                   UPDATE_PERSON_STATUS_BY_BALANCE= "UPDATE "+SQLiteHelper.TABLE_PERSON+" SET "+SQLiteHelper.COLUMN_P_STATUS
                            +" = 'Inactive' "+"WHERE "+SQLiteHelper.COLUMN_P_ID+" = "+p_idHolder;
                    sqLiteDatabase.execSQL(UPDATE_LOAN_STATUS_BY_BALANCE);
                    if(balance<=0){
                        sqLiteDatabase.execSQL(UPDATE_PERSON_STATUS_BY_BALANCE);
                    }
                    /*------------------------Insert into Attendance--------------------------------*/
                    int attPaymentID=0;
                    cursor=sqLiteDatabase.rawQuery("SELECT "+SQLiteHelper.COLUMN_PAYMENT_ID+" FROM "+SQLiteHelper.TABLE_PAYMENT,null);
                    if(cursor.moveToFirst()){
                        do {
                            attPaymentID= cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_PAYMENT_ID));
                        }while (cursor.moveToNext());
                    }cursor.close();
                    insertIntoAttendance(attPaymentID);
                    Toast.makeText(AddPayment.this, "Payment Success", Toast.LENGTH_SHORT).show();

                }
              //  sqLiteHelper.insertIntoTableAttendance(paymentDate);

            Intent intent= new Intent(AddPayment.this, StaffView.class);
                startActivity(intent);

            }
        });
        cancelPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddPayment.this, StaffView.class);
                startActivity(intent);
            }
        });
    }

    public void PopulateLoadID(){
        LOAN_ID.clear();
        cursor=sqLiteDatabase.rawQuery("SELECT "+SQLiteHelper.COLUMN_L_ID+" FROM "+SQLiteHelper.TABLE_LOAN
                +" WHERE "+SQLiteHelper.COLUMN_L_STATUS+" = 'Active'", null);
        if(cursor.moveToFirst()){
            do{
                LOAN_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_ID)));
            }while (cursor.moveToNext());
        }


    }
    /*----------------------Populate BorrowrName for Loan Listview--------------------*/
    public void PopulatePersonName(){
        int i=0;
        LOAN_P_ID.clear();
        P_NAME.clear();
        cursor=sqLiteDatabase.rawQuery("SELECT "+SQLiteHelper.COLUMN_L_PID+" FROM "+SQLiteHelper.TABLE_LOAN
                +" WHERE "+SQLiteHelper.COLUMN_L_STATUS+" = 'Active'", null);
        if(cursor.moveToFirst()){
            do{
                LOAN_P_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_PID)));
            }while(cursor.moveToNext());
            cursor.close();
        }
        if(LOAN_P_ID.size()>0) {
            do {
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_PERSON + " WHERE "
                        + SQLiteHelper.COLUMN_P_ID + " = " + LOAN_P_ID.get(i), null);
                cursor.moveToFirst();
                p_fname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME));
                p_mname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME));
                p_lname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME));
                P_NAME.add(p_fname + " " + p_mname + " " + p_lname);
                i++;
            } while (i < LOAN_P_ID.size());
        }
    }
    /*----------------------Populate StaffName for Loan Listview--------------------*/
    public void PopulateStaffName(){
        int i=0;
        cursor=sqLiteDatabase.rawQuery("SELECT "+SQLiteHelper.COLUMN_L_SID+" FROM "+SQLiteHelper.TABLE_LOAN
                +" WHERE "+SQLiteHelper.COLUMN_L_STATUS+" = 'Active'", null);
        LOAN_S_ID.clear();
        S_NAME.clear();
        if(cursor.moveToFirst()){
            do{
                LOAN_S_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_SID)));
            }while(cursor.moveToNext());
            cursor.close();
        }
        if(LOAN_S_ID.size()>0) {
            do {
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_PERSON +
                        " WHERE " + SQLiteHelper.COLUMN_P_ID + " = " + LOAN_S_ID.get(i), null);
                cursor.moveToFirst();
                s_fname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME));
                s_mname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME));
                s_lname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME));
                S_NAME.add(s_fname + " " + s_mname + " " + s_lname);
                i++;
            } while (i < LOAN_S_ID.size());
        }
    }
    /*----------------------Populate RefName for Loan Listview--------------------*/
    public void PopulateRefName(){
        int i=0;
        cursor=sqLiteDatabase.rawQuery("SELECT "+SQLiteHelper.COLUMN_L_RID+" FROM "+SQLiteHelper.TABLE_LOAN
                +" WHERE "+SQLiteHelper.COLUMN_L_STATUS+" = 'Active'", null);
        LOAN_R_ID.clear();
        R_NAME.clear();
        if(cursor.moveToFirst()){
            do{
                LOAN_R_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_RID)));
            }while(cursor.moveToNext());
            cursor.close();

        }
        Toast.makeText(AddPayment.this, LOAN_R_ID.size()+"", Toast.LENGTH_LONG).show();
        if(LOAN_R_ID.size()>0) {
            do {
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_PERSON + " WHERE " + SQLiteHelper.COLUMN_P_ID
                        + " = " + LOAN_R_ID.get(i), null);
                cursor.moveToFirst();
                r_fname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME));
                r_mname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME));
                r_lname = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME));
                R_NAME.add(r_fname + " " + r_mname + " " + r_lname);
                i++;
            } while (i < LOAN_R_ID.size());
        }
    }
    /*-----------------------------Populate Arrays foe Loan view----------------------*/
    public void PopulateOtherArrays() {
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_LOAN
                +" WHERE "+SQLiteHelper.COLUMN_L_STATUS+" = 'Active'", null);
        L_DDATE.clear();
        L_LDATE.clear();
        L_STATUS.clear();
        L_TYPE.clear();
        L_AMOUNT.clear();
        L_INTRATE.clear();
        L_INTAMOUNT.clear();
        L_TOTAL.clear();
        L_BALANCE.clear();
        if (cursor.moveToFirst()) {
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
            } while (cursor.moveToNext());


        }
    }
    /*-------------------------Populate Arrays for Payment Receiver----------------------------------*/
    public void PopulatePersonArray(){
        cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_PERSON
                +" WHERE "+SQLiteHelper.COLUMN_P_TYPE+" = 'Staff'", null);
        PERSON_FNAME.clear();
        PERSON_MNAME.clear(); PERSON_LNAME.clear(); PERSON_TYPE.clear(); PERSON_STATUS.clear();
        PERSON_CREDIT.clear();
        PERSON_ID.clear();
        if(cursor.moveToFirst()){
            do {
                PERSON_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_ID)));
                PERSON_CREDIT.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_CREDIT)));
                PERSON_FNAME.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME)));
                PERSON_MNAME.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME)));
                PERSON_LNAME.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME)));
                PERSON_TYPE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_TYPE)));
                PERSON_STATUS.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_STATUS)));

            }while (cursor.moveToNext());
        }


    }
    public void insertIntoAttendance(int pid) {
        String ltype="";
        int days=0;
        double daily_payment;
        double totalloanamount=0.0, loanbalance=0.0;
        String loanDate="";
        Double amountPaid;
        cursor= sqLiteDatabase.rawQuery("SELECt * FROM " +SQLiteHelper.TABLE_LOAN+" WHERE "
                +SQLiteHelper.COLUMN_L_ID+" = "+loan_ID, null);
        if(cursor.moveToFirst()) {
            loanbalance=cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_BALANCE));
            ltype=cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_TYPE));
            totalloanamount=cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_TOTAL));
            loanDate=cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_L_DATE));
        }cursor.close();
        if(ltype.equals("Quick Loan"))
            days=7;
        else days=30;
        daily_payment= totalloanamount/days;
        amountPaid= totalloanamount-(loanbalance+Double.parseDouble(paymentamount.getText().toString()));
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date loanD=null;
        Date today1= new Date();
        try {
            loanD = formatter.parse(loanDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        formatter.format(today1);
        long daysbetween= today1.getTime()-loanD.getTime();
        long seconds=daysbetween/1000;
        long minutes=seconds/60;
        long hours= minutes/60;
        long dayCount=(hours/24);
        Toast.makeText(AddPayment.this, daily_payment+"",Toast.LENGTH_SHORT).show();
        Double amountExpected= (daily_payment*dayCount)-amountPaid;
        String date1="";
        Double expec=0.0;
        sqLiteHelper.insertIntoTableAttendance(sqLiteDatabase, paymentdate.getText().toString(),amountExpected,"advanced", pid );
        cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_ATTENDANCE, null);
        if(cursor.moveToFirst()){
            do{
            date1= cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_ATTENDANCE_DATE));
            expec= cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_ATTENDANCE_AMOUNT_EXPEC));
            }while (cursor.moveToNext());
            cursor.close();
            Toast.makeText(AddPayment.this, "Date: "+date1+" Expected: "+expec+" Person: " +pid+" Paid: "+paymentamount.getText(), Toast.LENGTH_LONG).show();
        }
    }

    }
