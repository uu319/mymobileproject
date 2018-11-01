package com.example.sidney.lendingappfinal;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Sidney on 5/2/2018.
 */

public class AdminView extends AppCompatActivity {
    Button addStaff, addLoan, displayPerson, displayLoan, displayPayment, displayReport, displaycdReport;
    TextView logout;
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminview);
        addStaff= findViewById(R.id.addStaff);
        displayPerson=findViewById(R.id.displayPerson);
        displayLoan=findViewById(R.id.displayLoan);
        displayReport= findViewById(R.id.displayReport);
        logout= findViewById(R.id.logout);
        displaycdReport= findViewById(R.id.displayCDReport);
        displayPayment= findViewById(R.id.displayPayment);

        displaycdReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminView.this, CDReportDisplay.class);
                startActivity(intent);
            }
        });

        displayReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminView.this, ReportDisplay.class);
                startActivity(intent);
            }
        });

        displayPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminView.this, PaymentDisplay.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminView.this, LoginForm.class);
                startActivity(intent);
            }
        });

        displayLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(AdminView.this, LoanDisplay.class);
                startActivity(intent);
            }
        });
        addStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminView.this, AddCustomerForm.class);
              String type= "Staff";
              int test=2;
                intent.putExtra("type", type);
                intent.putExtra("test", test);
                startActivity(intent);
            }
        });

        displayPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminView.this, PersonDisplay.class);
                startActivity(intent);
            }
        });



    }

}
