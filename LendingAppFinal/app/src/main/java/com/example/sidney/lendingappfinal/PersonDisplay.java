package com.example.sidney.lendingappfinal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sidney on 5/2/2018.
 */

public class PersonDisplay extends AppCompatActivity {
    SQLiteHelper sqLiteHelper;
    String queryHolder;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<Integer> ID_ARRAY;
    ArrayList<String> FNAME_ARRAY;
    ArrayList<String> MNAME_ARRAY;
    ArrayList<String> LNAME_ARRAY;
    ArrayList<String> GENDER_ARRAY;
    ArrayList<String> BRGY_ARRAY;
    ArrayList<String> STREET_ARRAY;
    ArrayList<String> HNUM_ARRAY;
    ArrayList<String> PHONE_ARRAY;
    ArrayList<String> TYPE_ARRAY;
    ArrayList<String> STATUS_ARRAY;
    ArrayList<Double> CREDIT_ARRAY;
    ArrayList<String> SPOUSE_ARRAY;
    ArrayList<String> WORK_ARRAY;
    ArrayList<String> COMPANY_ARRAY;
    ArrayList<String> POSITION_ARRAY;
    String lname, fname, mname;
    ListView listView1;
    PersonListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        GENDER_ARRAY= new ArrayList<String>();
        BRGY_ARRAY=new ArrayList<String>();
        STREET_ARRAY= new ArrayList<String>();
        HNUM_ARRAY= new ArrayList<String>();
        PHONE_ARRAY= new ArrayList<String>();
        SPOUSE_ARRAY= new ArrayList<String>();
        WORK_ARRAY= new ArrayList<String>();
        COMPANY_ARRAY= new ArrayList<String>();
        ID_ARRAY= new ArrayList<Integer>();
        FNAME_ARRAY= new ArrayList<String>();
        MNAME_ARRAY= new ArrayList<String>();
        LNAME_ARRAY= new ArrayList<String>();
        TYPE_ARRAY= new ArrayList<String>();
        STATUS_ARRAY= new ArrayList<String>();
        CREDIT_ARRAY= new ArrayList<Double>();
        POSITION_ARRAY= new ArrayList<String>();


        sqLiteHelper= new SQLiteHelper(this);
        sqLiteDatabase=sqLiteHelper.getWritableDatabase();
        PopulateArray();
        listView1=findViewById(R.id.listview1);
        adapter= new PersonListAdapter(ID_ARRAY, FNAME_ARRAY, MNAME_ARRAY, LNAME_ARRAY, TYPE_ARRAY, STATUS_ARRAY,CREDIT_ARRAY, this);
        listView1.setAdapter(adapter);
        
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(PersonDisplay.this);
                View view1= getLayoutInflater().inflate(R.layout.displayperson, null);
                builder.setView(view1);
                TextView personid, personname,persongender, personaddress,personphone, persontype, personstatus, personcredit,
                        personspouse, personwork, personcompany, personposition;
                personid= view1.findViewById(R.id.personid);
                personname= view1.findViewById(R.id.personname);
                persongender= view1.findViewById(R.id.persongender);
                personaddress= view1.findViewById(R.id.personaddress);
                personphone= view1.findViewById(R.id.personphone);
                persontype= view1.findViewById(R.id.persontype);
                personstatus= view1.findViewById(R.id.personstatus);
                personcredit= view1.findViewById(R.id.personcredit);
                personspouse= view1.findViewById(R.id.personspouse);
                personwork= view1.findViewById(R.id.personwork);
                personcompany= view1.findViewById(R.id.personcompany);
                personposition= view1.findViewById(R.id.personpos);

                String  fname, mname, lname, gender, brgy, street, hnum, phone, type, status, spouse, work,company;
                int pid;
                double credit;
                pid=ID_ARRAY.get(position);
                fname= FNAME_ARRAY.get(position);mname= MNAME_ARRAY.get(position);lname= LNAME_ARRAY.get(position);
                gender= GENDER_ARRAY.get(position);
                brgy= BRGY_ARRAY.get(position);street= STREET_ARRAY.get(position);hnum= HNUM_ARRAY.get(position);
                phone= PHONE_ARRAY.get(position);type= TYPE_ARRAY.get(position);status= STATUS_ARRAY.get(position);
                credit= CREDIT_ARRAY.get(position);spouse= SPOUSE_ARRAY.get(position);work= WORK_ARRAY.get(position);
                company=COMPANY_ARRAY.get(position);
                personid.setText(pid+" "); personname.setText(fname+" "+mname+" "+lname); persongender.setText(gender);
                persongender.setText(gender);personaddress.setText(brgy+", "+street+", "+hnum);
                personphone.setText(phone);persontype.setText(type);personstatus.setText(status);
                personspouse.setText(spouse);personwork.setText(work);personcompany.setText(company);
                personcredit.setText(credit+"");
                personposition.setText(POSITION_ARRAY.get(position));
                AlertDialog dialog= builder.create();
                dialog.show();

            }
        });
    }

    public void PopulateArray(){
        ID_ARRAY.clear();
        FNAME_ARRAY.clear();
        MNAME_ARRAY.clear();
        LNAME_ARRAY.clear();
        TYPE_ARRAY.clear();
        STATUS_ARRAY.clear();
        CREDIT_ARRAY.clear();
        GENDER_ARRAY.clear();
        BRGY_ARRAY.clear();
        STREET_ARRAY.clear();
        HNUM_ARRAY.clear();
        PHONE_ARRAY.clear();
        SPOUSE_ARRAY.clear();
        WORK_ARRAY.clear();
        COMPANY_ARRAY.clear();
        POSITION_ARRAY.clear();

        queryHolder= "SELECT * FROM "+SQLiteHelper.TABLE_PERSON;
        cursor=sqLiteDatabase.rawQuery(queryHolder, null);
        if(cursor.moveToFirst()){
            do{
                COMPANY_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_COMPANY)));
                WORK_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_WORK)));
                SPOUSE_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_SPOUSE)));
                PHONE_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_NUM)));
                HNUM_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_HNUM)));
                STREET_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_STREET)));
                BRGY_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_BRGY)));
                GENDER_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_GENDER)));
                POSITION_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_POISITION)));

                ID_ARRAY.add(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_ID)));
                TYPE_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_TYPE)));
                STATUS_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_STATUS)));
                FNAME_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_FNAME)));
                MNAME_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_MNAME)));
                LNAME_ARRAY.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_P_LNAME)));
                CREDIT_ARRAY.add(cursor.getDouble(cursor.getColumnIndex(sqLiteHelper.COLUMN_P_CREDIT)));

            }while (cursor.moveToNext());
            cursor.close();
        }


    }
}