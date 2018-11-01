package com.example.sidney.lendingappfinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Sidney on 5/2/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME= "MyDatabase";

    /*-----------------------------Person Table*-----------------------------*/
    public static final String TABLE_PERSON="TablePerson";
    public static final String COLUMN_P_ID="p_id";
    public static final String COLUMN_P_LNAME="p_lname";
    public static final String COLUMN_P_FNAME="p_fname";
    public static final String COLUMN_P_MNAME="p_mname";
    public static final String COLUMN_P_GENDER="p_gender";
    public static final String COLUMN_P_BRGY="p_brgy";
    public static final String COLUMN_P_STREET="p_street";
    public static final String COLUMN_P_HNUM="p_hnum";
    public static final String COLUMN_P_NUM="p_num";
    public static final String COLUMN_P_TYPE="p_type";
    public static final String COLUMN_P_STATUS="p_status";
    public static final String COLUMN_P_CREDIT="p_credit";
    public static final String COLUMN_P_SPOUSE="p_spouse";
    public static final String COLUMN_P_WORK="p_work";
    public static final String COLUMN_P_COMPANY="p_company";
    public static final String COLUMN_P_POISITION="p_position";
    public static final String COLUMN_P_PASSWORD="p_password";

    /*-----------------------------Person Table*-----------------------------*/
    public static final String TABLE_LOAN="TableLoan";

    public static final String COLUMN_L_ID="l_id";
    public static final String COLUMN_L_PID="l_pid";
    public static final String COLUMN_L_SID="l_sid";
    public static final String COLUMN_L_RID="l_rid";
    public static final String COLUMN_L_DATE="l_date";
    public static final String COLUMN_L_DUE="l_due";
    public static final String COLUMN_L_AMOUNT="l_amount";
    public static final String COLUMN_L_INTRATE="l_intrate";
    public static final String COLUMN_L_INTAMOUNT="l_intamount";
    public static final String COLUMN_L_TOTAL="l_total";
    public static final String COLUMN_L_TYPE="l_type";
    public static final String COLUMN_L_BALANCE="l_balance";
    public static final String COLUMN_L_STATUS  ="l_status";

    /*-----------------------------Payment Table*-----------------------------*/
    public static final String TABLE_PAYMENT="TablePayment";

    public static final String COLUMN_PAYMENT_ID="payment_id";
    public static final String COLUMN_PAYMENT_DATE="payment_date";
    public static final String COLUMN_PAYMENT_AMOUNT="payment_amount";
    public static final String COLUMN_PAYMENT_lID="payment_lid";
    public static final String COLUMN_PAYMENT_SID="payment_sid";

   /*-----------------------------Payment Table*-----------------------------*/
    public static final String TABLE_ATTENDANCE="TableAttendance";

    public static final String COLUMN_ATTENDANCE_ID="att_id";
    public static final String COLUMN_ATTENDANCE_DATE="att_date";
    public static final String COLUMN_ATTENDANCE_AMOUNT_EXPEC="att_amount_expec";
    public static final String COLUMN_ATTENDANCE_STATUS="att_status";
    public static final String COLUMN_ATTENDANCE_PID="attendance_pid";

    public static final String TABLE_REPORT= "TableRepor";

    public static String COLUMN_REPORT_ID= "r_id";
    public static String COLUMN_REPORT_DATE= "r_date";
    public static String COLUMN_REPORT_ACTION= "r_action";
    public static String COLUMN_REPORT_ACTION_ID= "r_action_id";

    public static final String TABLE_CDREPORT="CustomerDueReport";

    public static final String COLUMN_CD_REPORT_ID="cd_id";
    public static final String COLUMN_CD_PERSON_ID= "cdPersonID";
    public static final String COLUMN_CD_LOAN_DATE="cdLoanDate";
    public static final String COLUMN_CD_LOAN_DUE="cdLoanDue";
    public static final String COLUMN_CD_LOAN_BALANCE="cdBalance";
    public static final String COLUMN_CD_STATUS= "cdStatus";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_REPORT= "CREATE TABLE IF NOT EXISTS "+TABLE_REPORT+"("
                +COLUMN_REPORT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_REPORT_DATE+" VARCHAR, "+COLUMN_REPORT_ACTION+" VARCHAR, "
                +COLUMN_REPORT_ACTION_ID+" VARCHAR)";

        String  CREATE_TABLE_CD_REPORT= "CREATE TABLE IF NOT EXISTS "+TABLE_CDREPORT+"("
                +COLUMN_CD_REPORT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_CD_PERSON_ID+" INTEGER, "
                +COLUMN_CD_LOAN_DATE+" DATE, "
                +COLUMN_CD_LOAN_DUE+" DATE, "
                +COLUMN_CD_LOAN_BALANCE+" REAL, "+COLUMN_CD_STATUS+" VARCHAR)";

        String CREATE_TABLE_PAYMENT= "CREATE TABLE IF NOT EXISTS "+TABLE_PAYMENT+" ("
                +COLUMN_PAYMENT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_PAYMENT_DATE+" DATE, "+COLUMN_PAYMENT_AMOUNT+" REAL, "
                +COLUMN_PAYMENT_lID+" INTEGER, "+COLUMN_PAYMENT_SID+" INTEGER)";

        String CREATE_TABLE_PERSON= "CREATE TABLE IF NOT EXISTS "+TABLE_PERSON+" ("
                +COLUMN_P_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_P_LNAME+" VARCHAR, "+COLUMN_P_FNAME+" VARCHAR, "+COLUMN_P_MNAME+" VARCHAR, "
                +COLUMN_P_GENDER+" VARCHAR, "+COLUMN_P_BRGY+" VARCHAR, "+COLUMN_P_STREET+" VARCHAR, "
                +COLUMN_P_HNUM+" VARCHAR, "+COLUMN_P_NUM+" VARCHAR, "+COLUMN_P_TYPE+" VARCHAR, "
                +COLUMN_P_STATUS+" VARCHAR, "+COLUMN_P_CREDIT+" REAL, "+COLUMN_P_SPOUSE+" VARCHAR, "
                +COLUMN_P_WORK+" VARCHAR, "+COLUMN_P_COMPANY+" VARCHAR, "+COLUMN_P_PASSWORD+" TEXT, "
                +COLUMN_P_POISITION+" VARCHAR)";

        String CREATE_TABLE_LOAN= "CREATE TABLE IF NOT EXISTS "+TABLE_LOAN+"("
                +COLUMN_L_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_L_PID+" INTEGER, "+COLUMN_L_SID+" INTEGER, "+COLUMN_L_RID+" INTEGER, "
                +COLUMN_L_DATE+" DATE, "+COLUMN_L_DUE+" VARCHAR, "+COLUMN_L_AMOUNT+" REAL, "
                +COLUMN_L_INTRATE+" REAL, "+COLUMN_L_INTAMOUNT+" REAL, "
                +COLUMN_L_TOTAL+" REAL, "+COLUMN_L_TYPE+" VARCHAR, "
                +COLUMN_L_BALANCE+" REAL, "+COLUMN_L_STATUS+" VARCHAR)";

        String CREATE_TABLE_ATTENDANCE= "CREATE TABLE IF NOT EXISTS "+TABLE_ATTENDANCE+"("
                +COLUMN_ATTENDANCE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_ATTENDANCE_DATE+" VARCHAR, "
                +COLUMN_ATTENDANCE_AMOUNT_EXPEC+" VARCHAR, "+COLUMN_ATTENDANCE_STATUS
                +" VARCHAR, "+COLUMN_ATTENDANCE_PID+" INTEGER)";

        String CREATE_TRIGGER_LOAN= "CREATE TRIGGER IF NOT EXISTS UPDATE_PERSON_STATUS " +
                "AFTER INSERT ON "+TABLE_LOAN+" FOR EACH ROW " +
                "BEGIN "+
                "UPDATE "+TABLE_PERSON+" SET "+ COLUMN_P_STATUS+" = 'Active'"+ " WHERE "+COLUMN_P_ID+" = NEW."+COLUMN_L_PID+";"+
                "UPDATE "+TABLE_PERSON+" SET "+COLUMN_P_CREDIT+" = "+0.0+" WHERE "+COLUMN_P_ID+" = NEW."+COLUMN_L_PID+";"+
                "UPDATE "+TABLE_CDREPORT+" SET "+COLUMN_CD_STATUS+" = 'Settled' "+" WHERE "+COLUMN_CD_PERSON_ID+" = NEW."+COLUMN_L_PID+";"+
                " END";


        String CREATE_TRIGGER_REPORT_ON_INSERT_PERSON="CREATE  TRIGGER IF NOT EXISTS REPORT_ON_INSERT_PERSON "+
        "AFTER INSERT ON "+TABLE_PERSON+
        " FOR EACH ROW "+
                "BEGIN "+
                "INSERT INTO "+TABLE_REPORT+"(r_date, r_action, r_action_id) VALUES ((DateTime('now'))"+", 'Added Person', 'New."+COLUMN_P_ID+"');"+
        " END";
        String CREATE_TRIGGER_REPORT_ON_INSERT_LOAN="CREATE  TRIGGER IF NOT EXISTS REPORT_ON_INSERT_LOAN "+
                "AFTER INSERT ON "+TABLE_LOAN+
                " FOR EACH ROW "+
                "BEGIN "+
                "INSERT INTO "+TABLE_REPORT+"(r_date, r_action, r_action_id) VALUES ((DateTime('now'))"+", 'Added Loan', 'New."+COLUMN_L_ID+"');"+
                " END";

      /*  String INSERT_INTO_CD_REPORT_ON_LOAN_STATYS_UPDATE= "CREATE  TRIGGER INSERT_INTO_CD_REOIRT_ON_LOAN_STATUS_UPDATE " +
                "AFTER UPDATE OF "+ COLUMN_L_STATUS+" ON "+TABLE_LOAN+
        " FOR EACH ROW " +
                "BEGIN "+

                " INSERT INTO "+TABLE_CDREPORT+"(cdPersonID, cdLoanDate, cdLoanDue, cdBalance) VALUES" +
                        " (SELECT "+COLUMN_L_PID+","+COLUMN_L_DATE+","+COLUMN_L_DUE+", "+COLUMN_L_BALANCE+" FROM INSERTED"+
        " END";*/

        String CREATE_TRIGGER_REPORT_ON_INSERT_PAYMENT="CREATE  TRIGGER IF NOT EXISTS REPORT_ON_INSERT_PAYMENT "+
                "AFTER INSERT ON "+TABLE_PAYMENT+
                " FOR EACH ROW "+
                "BEGIN "+
                "INSERT INTO "+TABLE_REPORT+"(r_date, r_action, r_action_id) VALUES ((DateTime('now'))"+", 'Added Payment', 'New."
                +COLUMN_PAYMENT_ID+"');"+
                " END";
        String CREATE_TRIGGER_REPORT_ON_INSERT_ATTENDANCE="CREATE  TRIGGER IF NOT EXISTS REPORT_ON_INSERT_ATTENDANCE "+
                "AFTER INSERT ON "+TABLE_ATTENDANCE+
                " FOR EACH ROW "+
                "BEGIN "+
                "INSERT INTO "+TABLE_REPORT+"(r_date, r_action, r_action_id) VALUES ((DateTime('now'))"+", 'UPDATED ATTENDANCE', 'New."
                +COLUMN_ATTENDANCE_ID+"');"+
                " END";

        db.execSQL(CREATE_TABLE_PERSON);
        db.execSQL(CREATE_TABLE_LOAN);
        db.execSQL(CREATE_TABLE_PAYMENT);
        db.execSQL(CREATE_TRIGGER_LOAN);
        db.execSQL(CREATE_TABLE_ATTENDANCE);
        db.execSQL(CREATE_TABLE_REPORT);
        db.execSQL(CREATE_TRIGGER_REPORT_ON_INSERT_PERSON);
        db.execSQL(CREATE_TRIGGER_REPORT_ON_INSERT_LOAN);
        db.execSQL(CREATE_TRIGGER_REPORT_ON_INSERT_PAYMENT);
        db.execSQL(CREATE_TRIGGER_REPORT_ON_INSERT_ATTENDANCE);
        db.execSQL(CREATE_TABLE_CD_REPORT);
        db.execSQL(CREATE_TRIGGER_REPORT_ON_INSERT_PAYMENT);



    }
    public void insertIntoTableLoan(SQLiteDatabase sqLiteDatabase, int pid, int sid, int rid, String date,
                                    String due, double amount, double intrate, double intamount, double total, String type,
                                    double balance, String status){
        String queryHolder= "INSERT INTO "+TABLE_LOAN+"(l_pid, l_sid, l_rid, l_date, l_due, l_amount, l_intrate, " +
                "l_intamount, l_total, l_type, l_balance, l_status) VALUES('"
                +pid+"', '"+sid+"', '"+rid+"', '"+date+"', '"+due+"','"+amount+"', '"+intrate+"', '"+intamount+"', '"
                +total+"', '"+type+"', '"+balance+"', '"+status+"');";
        sqLiteDatabase.execSQL(queryHolder);
    }

    public void insertIntoCDReport(SQLiteDatabase sqLiteDatabase, int cdPersonID, String loanDate, String loanDue, Double loanBalance,
                                   String status){

        String queryHolder= "INSERT INTO "+TABLE_CDREPORT+"(cdPersonID, cdLoanDate, cdLoanDue, cdBalance, cdStatus) VALUES('"
                +cdPersonID+"', '"+loanDate+"', '"+loanDue+"', '"+loanBalance+"', '"+status+"');";
        sqLiteDatabase.execSQL(queryHolder);

    }
    public void insertIntoTablePerson(SQLiteDatabase sqLiteDatabase, String lname, String fname, String mname, String gender,
                                      String brgy, String street, String hnum, String num,
                                      String type, String status, double credit, String spouse,
                                      String work, String company, String password, String position){
        String queryHolder= "INSERT INTO "+TABLE_PERSON+"(p_lname, p_fname, p_mname, p_gender, p_brgy, p_street, p_hnum" +
                ", p_num, p_type, p_status, p_credit, p_spouse, p_work, p_company, p_password, p_position) VALUES('"
                +lname+"', '"+fname+"', '"+mname+"', '"+gender+"', '"+brgy+"', '"+street+"', '"
                +hnum+"', '"+num+"', '"+type+"', '"+status+"', '"+credit+"', '"+spouse+"', '"
                +work+"', '"+company+"', '"+password+"', '"+position+"');";
        sqLiteDatabase.execSQL(queryHolder);

    }
    public void insertIntoTableAttendance(SQLiteDatabase sqLiteDatabase, String date, Double amount_expec, String status, int pid){
        String queryHolder="INSERT INTO "+TABLE_ATTENDANCE+"(att_date, att_amount_expec, att_status, attendance_pid) " +
                "VALUES ('"+date+"', '"+amount_expec+"', '"+status+"', '"+pid+"');";
        sqLiteDatabase.execSQL(queryHolder);
    }

    public void insertIntoPayment(SQLiteDatabase sqLiteDatabase, String date, double amount, int lid, int sid){
        String queryHolder= "INSERT INTO "+TABLE_PAYMENT+"(payment_date, payment_amount, payment_lid, payment_sid)" +
                " VALUES('"+date+"', '"+amount+"', '"+lid+"', '"+sid+"');";
        sqLiteDatabase.execSQL(queryHolder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PERSON);
        onCreate(db);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar today = Calendar.getInstance();
        String dateToday = sdf.format(today.getTime());
        String UPDATE_LOAN_STATUS_BY_DATE="UPDATE "+SQLiteHelper.TABLE_LOAN+" SET "+SQLiteHelper.COLUMN_L_STATUS+" = 'Inactive' WHERE "
                +SQLiteHelper.COLUMN_L_DUE +" = "+"'"+dateToday+"'";
        db.execSQL(UPDATE_LOAN_STATUS_BY_DATE);
    }
}
