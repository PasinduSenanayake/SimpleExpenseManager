package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

/**
 * Created by Pasindu on 11/18/2016.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ExpenseManager.db";
    public static final String CONTACTS_TABLE_NAME = "transactions";
    public static final String CONTACTS_COLUMN_AC = "accountNo";
    public static final String CONTACTS_COLUMN_NAME = "bankName";
    public static final String CONTACTS_COLUMN_ACHNAME= "accountHolderName";
    public static final String CONTACTS_COLUMN_DAY = "day";
    public static final String CONTACTS_COLUMN_MONTH = "month";
    public static final String CONTACTS_COLUMN_YEAR= "year";
    public static final String CONTACTS_COLUMN_TYPE= "type";
    public static final String CONTACTS_COLUMN_BALANCE = "balance";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table account " +
                        "(accountNo char(6) primary key, bankName text, accountHolderName text, balance real)"
        );

        db.execSQL(
                "create table transactions " +
                        "(transID integer primary key AUTOINCREMENT, accountNo text NOT NULL, day text, month text , year text , type text, balance real)"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS account");
        onCreate(db);
    }

    //Insert New Account to Database
    public boolean insertAccount(String accountNo, String bankName, String accountHolderName, double balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountNo", accountNo);
        contentValues.put("bankName", bankName);
        contentValues.put("accountHolderName", accountHolderName);
        contentValues.put("balance", balance);
        db.insert("account", null, contentValues);
        return true;
    }


//Insert New Transaction to Log
    public boolean insertLog(String accountNo, String day, String month, String year ,String type,double balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountNo", accountNo);
        contentValues.put("day", day);
        contentValues.put("month", month);
        contentValues.put("year", year);
        contentValues.put("type", type);
        contentValues.put("balance", balance);
        db.insert("transactions", null, contentValues);
        return true;
    }


    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }




//Get all accounts from Database
    public ArrayList<String> getAllAccounts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from account", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_AC)));
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_ACHNAME)));
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_BALANCE)));

            res.moveToNext();
        }
        return array_list;
    }

//Get all Transactions from Database
    public ArrayList<String> getAllTransactions() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from transactions", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_AC)));
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_DAY )));
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_MONTH)));
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_YEAR)));
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_TYPE)));
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_BALANCE)));

            res.moveToNext();
        }
        return array_list;
    }
}