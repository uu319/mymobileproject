package com.example.sidney.lendingappfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sidney on 5/2/2018.
 */

public class AddLoan extends AppCompatActivity {
    /*---------Retrieve Person_ID and Person_ID---------*/
    PersonListAdapter adapter;
    ListView listView;
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ArrayList<Integer> PERSON_ID;
    ArrayList<Double> PERSON_CREDIT;
    ArrayList<String>  PERSON_FNAME, PERSON_MNAME, PERSON_LNAME, PERSON_TYPE, PERSON_STATUS;
    EditText l_name, l_type, l_ldate, l_ddate, l_amount, l_intRate, l_intAmount, l_total, l_ref, l_staff;
    Button l_save, l_cancel;
    String borrowerName, referrerName, staffName, queryHolder, statusHolder= "Active";
    int borrowerID, referrerID, staffID, l_amountHolder;
    double l_totalHolder, p_credit, lamount,intamount,intrate,ltotal;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar today = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addloan);
        sqLiteHelper= new SQLiteHelper(this);
        sqLiteDatabase=sqLiteHelper.getWritableDatabase();
        PERSON_ID= new ArrayList<Integer>();
        PERSON_FNAME= new ArrayList<String>();
        PERSON_MNAME= new ArrayList<String>();
        PERSON_LNAME= new ArrayList<String>();
        PERSON_TYPE= new ArrayList<String>();
        PERSON_STATUS= new ArrayList<String>();
        PERSON_CREDIT= new ArrayList<Double>();
        PopulateCustomerArrays();
        adapter= new PersonListAdapter(PERSON_ID, PERSON_FNAME,PERSON_MNAME, PERSON_LNAME, PERSON_TYPE, PERSON_STATUS, PERSON_CREDIT, this);
        l_name=findViewById(R.id.l_name);
        l_type=findViewById(R.id.l_type);
        l_ldate=findViewById(R.id.l_ldate);
        l_ddate=findViewById(R.id.l_ddate);
        l_amount=findViewById(R.id.l_amount);
        l_intRate=findViewById(R.id.l_intRate);
        l_intAmount=findViewById(R.id.l_intAmount);
        l_total=findViewById(R.id.l_total);
        l_ref=findViewById(R.id.l_ref);
        l_staff=findViewById(R.id.l_staff);
        l_save=findViewById(R.id.l_save);
        l_cancel=findViewById(R.id.l_cancel);

    //    sqLiteHelper.insertIntoTableLoan(sqLiteDatabase, borrowerID, staffID, referrerID, l_ldate.getText().toString(),
      //                                   l_ddate.getText().toString(), l_amount.getText(),l_intRate.getText(),l_intAmount,
        //                                 l_total.getText(), l_type.getText(), );

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar today = Calendar.getInstance();
        String loanDate = sdf.format(today.getTime());
        l_ldate.setText(loanDate);

        l_intRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddLoan.this, l_amount.getText()+"", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder= new AlertDialog.Builder(AddLoan.this);
                builder.setTitle("                    Interest Rate");
                final EditText inputIntRate= new EditText(AddLoan.this);
                inputIntRate.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(inputIntRate);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lamount=Double.parseDouble(l_amount.getText().toString());
                        double lamountwithcredit= lamount+p_credit;
                        intrate= (Double.parseDouble(inputIntRate.getText().toString()))/100;
                        l_intRate.setText(intrate+"");
                        intamount= lamountwithcredit*intrate;
                        ltotal= lamountwithcredit+intamount;
                        l_intAmount.setText(intamount+"");
                        l_total.setText(ltotal+"");
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        l_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddLoan.this, "Test", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder= new AlertDialog.Builder(AddLoan.this);
                View view= getLayoutInflater().inflate(R.layout.loantypedialog, null);
                builder.setView(view);
                final AlertDialog dialog= builder.create();
                dialog.show();
                Button confirm= view.findViewById(R.id.confirmType);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
        l_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopulateCustomerArrays();
                AlertDialog.Builder builder= new AlertDialog.Builder(AddLoan.this);
                View view= getLayoutInflater().inflate(R.layout.listview, null);
                builder.setView(view);
                listView= view.findViewById(R.id.listview1);
                listView.setAdapter(adapter);
                final AlertDialog dialog= builder.create();
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        borrowerID= PERSON_ID.get(position);
                        borrowerName=PERSON_FNAME.get(position)+" "+PERSON_MNAME.get(position)+" "+PERSON_FNAME.get(position);
                        p_credit= PERSON_CREDIT.get(position);
                        l_name.setText(borrowerName+", "+borrowerID);
                        dialog.dismiss();
                    }
                });
            }
        });
        l_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopulateStaffArrays();
                AlertDialog.Builder builder= new AlertDialog.Builder(AddLoan.this);
                View view= getLayoutInflater().inflate(R.layout.listview, null);
                builder.setView(view);
                listView= view.findViewById(R.id.listview1);
                listView.setAdapter(adapter);
                final AlertDialog dialog= builder.create();
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        staffID= PERSON_ID.get(position);
                        staffName=PERSON_FNAME.get(position)+" "+PERSON_MNAME.get(position)+" "+PERSON_FNAME.get(position);
                        l_staff.setText(staffName+", "+staffID);
                        dialog.dismiss();
                    }
                });
            }
        });

        l_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopulateRefArrays();
                AlertDialog.Builder builder= new AlertDialog.Builder(AddLoan.this);
                View view= getLayoutInflater().inflate(R.layout.listview, null);
                builder.setView(view);
                listView= view.findViewById(R.id.listview1);
                listView.setAdapter(adapter);
                final AlertDialog dialog= builder.create();
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        referrerID= PERSON_ID.get(position);
                        referrerName=PERSON_FNAME.get(position)+" "+PERSON_MNAME.get(position)+" "+PERSON_FNAME.get(position);
                        l_ref.setText(referrerName+", "+referrerID);
                        dialog.dismiss();
                    }
                });
            }
        });


        l_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* queryHolder= "UPDATE "+SQLiteHelper.TABLE_PERSON+" SET "+SQLiteHelper.COLUMN_P_STATUS+" = 'Active' WHERE "
                        +SQLiteHelper.COLUMN_P_ID+" = "+borrowerID;
                sqLiteDatabase.execSQL(queryHolder);*/
               // sqLiteHelper.personLoanStatusTrigger(sqLiteDatabase);
                if(l_name.getText().length()!=0 && l_staff.getText().length()!=0 && l_ref.getText().length()!=0 && l_ldate.getText().length()!=0 &&
                        l_ddate.getText().length()!=0 && l_amount.getText().length()!=0 && l_intRate.getText().length()!=0
                        && l_type.getText().length()!=0){
                sqLiteHelper.insertIntoTableLoan(sqLiteDatabase, borrowerID, staffID, referrerID, l_ldate.getText().toString(),
                                                 l_ddate.getText().toString(), lamount, intrate,
                                                 intamount, ltotal, l_type.getText().toString(),
                                                 ltotal, statusHolder);
                Toast.makeText(AddLoan.this, "Loan Added", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(AddLoan.this, StaffView.class);
                startActivity(intent);}else Toast.makeText(AddLoan.this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });


            l_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddLoan.this, StaffView.class);
                startActivity(intent);
            }
        });



    }
    public void PopulateCustomerArrays() {
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_PERSON + " WHERE "
                + SQLiteHelper.COLUMN_P_STATUS + " = " + "'Inactive' AND "
                +SQLiteHelper.COLUMN_P_TYPE+" = 'Customer'", null);
        PERSON_STATUS.clear();
        PERSON_TYPE.clear();
        PERSON_ID.clear();
        PERSON_FNAME.clear();
        PERSON_MNAME.clear();
        PERSON_LNAME.clear();
        PERSON_CREDIT.clear();
        if (cursor.moveToFirst()) {
            do {
                PERSON_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_ID)));
                PERSON_FNAME.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME)));
                PERSON_MNAME.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME)));
                PERSON_LNAME.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME)));
                PERSON_TYPE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_TYPE)));
                PERSON_STATUS.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_STATUS)));
                PERSON_CREDIT.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_CREDIT)));
            } while (cursor.moveToNext());
            cursor.close();

        }
    }
    public void PopulateStaffArrays() {
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_PERSON + " WHERE "
                + SQLiteHelper.COLUMN_P_TYPE + " = " + "'Staff' AND " + SQLiteHelper.COLUMN_P_STATUS
                + " = 'Active'", null);
        PERSON_STATUS.clear();
        PERSON_TYPE.clear();
        PERSON_ID.clear();
        PERSON_FNAME.clear();
        PERSON_MNAME.clear();
        PERSON_LNAME.clear();
        PERSON_CREDIT.clear();
        if (cursor.moveToFirst()) {
            do {
                PERSON_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_ID)));
                PERSON_FNAME.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME)));
                PERSON_MNAME.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME)));
                PERSON_LNAME.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME)));
                PERSON_TYPE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_TYPE)));
                PERSON_STATUS.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_STATUS)));
                PERSON_CREDIT.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_CREDIT)));
            } while (cursor.moveToNext());
            cursor.close();

        }
    }
    public void PopulateRefArrays() {
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_PERSON
                +" WHERE "+SQLiteHelper.COLUMN_P_ID+" <> "+borrowerID,null);
        PERSON_STATUS.clear();
        PERSON_TYPE.clear();
        PERSON_ID.clear();
        PERSON_FNAME.clear();
        PERSON_MNAME.clear();
        PERSON_LNAME.clear();
        PERSON_CREDIT.clear();
        if (cursor.moveToFirst()) {
            do {
                PERSON_ID.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_ID)));
                PERSON_FNAME.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME)));
                PERSON_MNAME.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME)));
                PERSON_LNAME.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME)));
                PERSON_TYPE.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_TYPE)));
                PERSON_STATUS.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_STATUS)));
                PERSON_CREDIT.add(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_CREDIT)));
            } while (cursor.moveToNext());
            cursor.close();

        }
    }
    public void onRadioClick(View view){
        boolean checked= ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.quickLoan:
                if(checked){
                    Calendar now= Calendar.getInstance();
                    now.add(Calendar.DATE, 7);
                    String dueDate = sdf.format(now.getTime());
                    l_ddate.setText(dueDate);
                    l_type.setText("Quick Loan");

                }break;
            case R.id.normalLoan:
                if(checked){
                    Calendar now= Calendar.getInstance();
                    now.add(Calendar.DATE, 30);
                    String dueDate = sdf.format(now.getTime());
                    l_ddate.setText(dueDate);
                    l_type.setText("Normal Loan");
                }
                break;


        }


    }
}
