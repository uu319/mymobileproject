package com.example.sidney.lendingappfinal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Sidney on 5/5/2018.
 */

public class LoginForm extends AppCompatActivity {
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    Button loginAsAdmin, loginAsStaff, loginAsCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sqLiteHelper= new SQLiteHelper(this);
        sqLiteDatabase= sqLiteHelper.getWritableDatabase();
        loginAsAdmin=findViewById(R.id.adminlogin);
        loginAsStaff=findViewById(R.id.stafflogin);
        loginAsCustomer=findViewById(R.id.customerlogin);

        loginAsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(LoginForm.this);
                View view1= getLayoutInflater().inflate(R.layout.logindialog, null);
                builder.setView(view1);
                final EditText username, password;
                Button login;
                login= view1.findViewById(R.id.login);
                username= view1.findViewById(R.id.username);
                password= view1.findViewById(R.id.p_password);
                AlertDialog dialog= builder.create();
                dialog.show();
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(LoginForm.this, AdminView.class);
                        if(username.getText().toString().equals("101") && password.getText().toString().equals("admin"))
                            startActivity(intent);
                        else
                        Toast.makeText(LoginForm.this, "Account does not exist",Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(LoginForm.this, username.getText(),Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        loginAsStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(LoginForm.this);
                View view= getLayoutInflater().inflate(R.layout.logindialog, null);
                builder.setView(view);
                final EditText username, password;
                Button login;
                login= view.findViewById(R.id.login);
                username= view.findViewById(R.id.username);
                password= view.findViewById(R.id.p_password);
                AlertDialog dialog= builder.create();
                dialog.show();
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((username.getText().toString().length()!=0)&&(password.getText().toString().length()!=0)){
                           cursor = sqLiteDatabase.rawQuery("SELECT * FROM "
                                    + SQLiteHelper.TABLE_PERSON + " WHERE "
                                    + SQLiteHelper.COLUMN_P_ID + " = " + username.getText() +
                                    " AND " + SQLiteHelper.COLUMN_P_PASSWORD + " = '" + password.getText() +
                                    "' AND " + SQLiteHelper.COLUMN_P_TYPE
                                    + " = 'Staff'", null);
                            if (cursor.moveToFirst()) {
                                Intent intent = new Intent(LoginForm.this, StaffView.class);
                                startActivity(intent);
                                Toast.makeText(LoginForm.this, "Success", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(LoginForm.this, "Account doesnt exist", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(LoginForm.this, "Both fields are required", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        loginAsCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(LoginForm.this);
                View view= getLayoutInflater().inflate(R.layout.logindialog, null);
                builder.setView(view);
                final EditText username, password;
                Button login;
                login= view.findViewById(R.id.login);
                username= view.findViewById(R.id.username);
                password= view.findViewById(R.id.p_password);
                AlertDialog dialog= builder.create();
                dialog.show();
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((username.getText().toString().length() != 0) && (password.getText().toString().length() != 0)) {
                            cursor = sqLiteDatabase.rawQuery("SELECT * FROM "
                                    + SQLiteHelper.TABLE_PERSON + " WHERE "
                                    + SQLiteHelper.COLUMN_P_ID + " = " + username.getText() +
                                    " AND " + SQLiteHelper.COLUMN_P_PASSWORD + " = '" + password.getText().toString() +
                                    "' AND " + SQLiteHelper.COLUMN_P_TYPE
                                    + " = 'Customer'", null);
                            if (cursor.moveToFirst()) {
                                int customer_id;
                                customer_id= cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_ID));
                                Intent intent = new Intent(LoginForm.this, CustomerView.class);
                                intent.putExtra("customerID", customer_id);
                                startActivity(intent);
                                Toast.makeText(LoginForm.this, "Success", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(LoginForm.this, "Account doesnt exist", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(LoginForm.this, "Both fields are required", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}
