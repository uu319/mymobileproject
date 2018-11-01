package com.example.sidney.lendingappfinal;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddCustomerForm extends AppCompatActivity {
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    EditText p_lname, p_fname, p_mname, p_brgy, p_street, p_hnum, p_num, p_spouse, p_work, p_company, p_position, p_password, p_type;
    RadioGroup p_gender;
    RadioButton p_male, p_female;
    Button p_save, p_cancel;
    String genderHolder, typeHolder, statusHolder, spouseholder;
    double creditHolder=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addperson2);

        sqLiteHelper= new SQLiteHelper(this);
        sqLiteDatabase=sqLiteHelper.getReadableDatabase();
        p_lname= findViewById(R.id.p_lname);
        p_fname= findViewById(R.id.p_fname);
        p_mname= findViewById(R.id.p_mname);
        p_brgy= findViewById(R.id.p_brgy);
        p_street= findViewById(R.id.p_street);
        p_hnum= findViewById(R.id.p_hnum);
        p_num= findViewById(R.id.p_num);
        p_spouse= findViewById(R.id.p_spouse);
        p_work= findViewById(R.id.p_work);
        p_company= findViewById(R.id.p_company);
        p_position= findViewById(R.id.p_position);
        p_gender= findViewById(R.id.p_gender);

        p_male= findViewById(R.id.p_male);
        p_female= findViewById(R.id.p_female);
        p_save= findViewById(R.id.p_save);
        p_cancel= findViewById(R.id.p_cancel);
        p_password= findViewById(R.id.p_password);
        p_type=findViewById(R.id.p_type);
        int num=0;
        Bundle b= getIntent().getExtras();
        if(b!=null) {
            String type = b.getString("type");
             num= b.getInt("test");
            p_type.setText(type);

            if(num==1){
                p_position.setText("None");
                p_position.setEnabled(false);
                statusHolder="Inactive";
            };
            if(num==2){
                p_work.setText("Staff");
                p_work.setEnabled(false);
                p_company.setText("Mobile Lending");
                p_company.setEnabled(false);
                statusHolder="Active";
            }


        }





        p_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!p_lname.getText().toString().isEmpty() && !p_fname.getText().toString().isEmpty() &&
                !p_mname.getText().toString().isEmpty()) && genderHolder.length()!=0 && p_brgy.getText().toString().length()!=0 &&
                p_street.getText().toString().length()!=0 && p_hnum.getText().toString().length()!=0 && p_num.getText().toString().length()!=0
                && p_type.getText().toString().length()!=0 && statusHolder.length()!=0 && p_spouse.getText().toString().length()!=0 &&
                p_work.getText().toString().length()!=0 && p_company.getText().toString().length()!=0 && p_password.getText().toString().length()!=0 &&
                p_position.getText().toString().length()!=0) {
                    sqLiteHelper.insertIntoTablePerson(sqLiteDatabase, p_lname.getText().toString(), p_fname.getText().toString(),
                            p_mname.getText().toString(), genderHolder, p_brgy.getText().toString(), p_street.getText().toString(),
                            p_hnum.getText().toString(), p_num.getText().toString(), p_type.getText().toString(),
                            statusHolder, creditHolder, p_spouse.getText().toString(), p_work.getText().toString(),
                            p_company.getText().toString(), p_password.getText().toString(), p_position.getText().toString());

                    Intent intent = new Intent(AddCustomerForm.this, AdminView.class);
                    Intent intent2 = new Intent(AddCustomerForm.this, StaffView.class);

                    Bundle b=getIntent().getExtras();
                            if(b!=null){
                                String type=b.getString("type");
                                int i=b.getInt("test");
                                if(i==2)
                                    startActivity(intent);
                                else startActivity(intent2);
                            }

                }else Toast.makeText(AddCustomerForm.this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });
        p_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddCustomerForm.this, AdminView.class);
                startActivity(intent);
            }
        });




    }
    public void onRadioClick(View view){
        boolean checked= ((RadioButton) view).isChecked();

        switch(view.getId()){
            case R.id.p_male:
                if(checked)
                    genderHolder="Male";
                break;
            case R.id.p_female:
                if(checked)
                    genderHolder="Female";
                break;
        }
    }
}
