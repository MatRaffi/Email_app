package sg.edu.rp.c346.s19024292.emailapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "user.db";

    private static final String TABLE_USER = "user";
    private static final String COLUMN_USERID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";


    private static final String TABLE_CONTACT = "contact";
    private static final String COLUMN_CONTACTID = "_id";
    private static final String COLUMN_USERCONTACTID = "user_id";
    private static final String COLUMN_CONTACTS = "contacts";
    Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_USER +  "("
                + COLUMN_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_EMAIL + " TEXT )";

        String createTableSqlContact = "CREATE TABLE " + TABLE_CONTACT +  "("
                + COLUMN_CONTACTID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERCONTACTID + " INTEGER,"
                + COLUMN_CONTACTS + " TEXT )";
        db.execSQL(createTableSql);
        db.execSQL(createTableSqlContact);

//        db.execSQL("create table "+TABLE_USER+" (USERID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, EMAIL TEXT)");
//        db.execSQL("create table "+TABLE_CONTACT+" (CONTACTID INTEGER PRIMARY KEY AUTOINCREMENT, USERCONTACTID INTEGER, CONTACTS TEXT)");

        Log.i("info" ,"created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS TABLE_USER");
        db.execSQL("DROP TABLE IF EXISTS TABLE_CONTACT");
        //Create table(s) again
        onCreate(db);
    }

    public Boolean insertUser(String username, String password, String email){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        long result = db.insert(TABLE_USER,null,values);
        if (result==-1){
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean insertContact(String contacts, int userId){
        Toast.makeText(context, userId + "", Toast.LENGTH_SHORT).show();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERCONTACTID, userId);
        values.put(COLUMN_CONTACTS, contacts);
        long result = db.insert(TABLE_CONTACT,null,values);
        if (result==-1){
            return false;
        }
        else {
            return true;
        }
    }

    public ArrayList<String> getContactById(int userid) {
        ArrayList<String> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT contacts FROM contact WHERE userid= ?",new String[]{String.valueOf(userid)});
        if (cursor.moveToFirst()) {
            do {
                String contact = cursor.getString(0);
                contacts.add(contact + "");
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }

    public Boolean checkusername (String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username = ?",new String[]{username});
        if (cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    public int checkemailpassword (String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("Select * from user ",null);
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email = ? AND password = ?",new String[]{email,password});
//        Toast.makeText(context,cursor.getCount()+"",Toast.LENGTH_SHORT).show();
        if (cursor.getCount()>0) {
            int id =0;
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(0);
                }
                while (cursor.moveToNext());
            }
            return id;
        } else {
            return 0;
        }
    }

}

