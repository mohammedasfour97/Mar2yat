package com.mriat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mriat.ModelClasses.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "user_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(User.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }


    public void insertUser(User user) {
        // get writable database as we want to write data
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(User.getCOLUMN_ActivateDaylight() , user.getActivateDaylight());
        values.put(User.getCOLUMN_ActivationCode() , user.getActivationCode());
        values.put(User.getCOLUMN_ActivationDate() , user.getActivationDate());
        values.put(User.getCOLUMN_AdminStatus() , user.getAdminStatus());
        values.put(User.getCOLUMN_AppearName() , user.getAppearName());
        values.put(User.getCOLUMN_Code() , user.getCode());
        values.put(User.getCOLUMN_Email() , user.getEmail());
        values.put(User.getCOLUMN_ID_Country() , user.getID_Country());
        values.put(User.getCOLUMN_ID_DateFormat() , user.getID_DateFormat());
        values.put(User.getCOLUMN_ID_DefaultStyle() , user.getID_DefaultStyle());
        values.put(User.getCOLUMN_ID_Gender() , user.getID_Gender());
        values.put(User.getCOLUMN_ID_TimeDifference() , user.getID_TimeDifference());
        values.put(User.getCOLUMN_ID_WeekBegin() , user.getID_WeekBegin());
        values.put(User.getCOLUMN_LastInteraction() , user.getLastInteraction());
        values.put(User.getCOLUMN_MonthFullName() , user.getMonthFullName());
        values.put(User.getCOLUMN_Password() , user.getPassword());
        values.put(User.getCOLUMN_picture() , user.getPicture());
        values.put(User.getCOLUMN_SignupDate() , user.getSignupDate());
        values.put(User.getCOLUMN_Status() , user.getStatus());
        values.put(User.getCOLUMN_UserName() , user.getUserName());
        values.put(User.getColumnId() , user.getID());
        values.put(User.getColumnIdUser() , user.getID_USER());

        // insert row
        db.insert(User.TABLE_NAME, null, values);

        // close db connection
        db.close();


        // return newly inserted row id
    }


    public List<User> getUser() {
        // get readable database as we are not inserting anything
        List<User> users = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + User.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setID(cursor.getString(cursor.getColumnIndex(User.COLUMN_ID)));
                user.setUserName(cursor.getString(cursor.getColumnIndex(User.COLUMN_UserName)));
                user.setStatus(cursor.getString(cursor.getColumnIndex(User.COLUMN_Status)));
                user.setSignupDate(cursor.getString(cursor.getColumnIndex(User.COLUMN_SignupDate)));
                user.setPicture(cursor.getString(cursor.getColumnIndex(User.COLUMN_picture)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(User.COLUMN_Password)));
                user.setMonthFullName(cursor.getString(cursor.getColumnIndex(User.COLUMN_MonthFullName)));
                user.setLastInteraction(cursor.getString(cursor.getColumnIndex(User.COLUMN_LastInteraction)));
                user.setID_WeekBegin(cursor.getString(cursor.getColumnIndex(User.COLUMN_ID_WeekBegin)));
                user.setID_USER(cursor.getString(cursor.getColumnIndex(User.COLUMN_ID_USER)));
                user.setID_TimeDifference(cursor.getString(cursor.getColumnIndex(User.COLUMN_ID_TimeDifference)));
                user.setID_Gender(cursor.getString(cursor.getColumnIndex(User.COLUMN_ID_Gender)));
                user.setID_DefaultStyle(cursor.getString(cursor.getColumnIndex(User.COLUMN_ID_DefaultStyle)));
                user.setID_DateFormat(cursor.getString(cursor.getColumnIndex(User.COLUMN_ID_DateFormat)));
                user.setID_Country(cursor.getString(cursor.getColumnIndex(User.COLUMN_ID_Country)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(User.COLUMN_Email)));
                user.setCode(cursor.getString(cursor.getColumnIndex(User.COLUMN_Code)));
                user.setAppearName(cursor.getString(cursor.getColumnIndex(User.COLUMN_AppearName)));
                user.setAdminStatus(cursor.getString(cursor.getColumnIndex(User.COLUMN_AdminStatus)));
                user.setActivationDate(cursor.getString(cursor.getColumnIndex(User.COLUMN_ActivationDate)));
                user.setActivationCode(cursor.getString(cursor.getColumnIndex(User.COLUMN_ActivationCode)));
                user.setActivateDaylight(cursor.getString(cursor.getColumnIndex(User.COLUMN_ActivateDaylight)));
                users.add(user);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return users;
    }


    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.getCOLUMN_ActivateDaylight() , user.getActivateDaylight());
        values.put(User.getCOLUMN_ActivationCode() , user.getActivationCode());
        values.put(User.getCOLUMN_ActivationDate() , user.getActivationDate());
        values.put(User.getCOLUMN_AdminStatus() , user.getAdminStatus());
        values.put(User.getCOLUMN_AppearName() , user.getAppearName());
        values.put(User.getCOLUMN_Code() , user.getCode());
        values.put(User.getCOLUMN_Email() , user.getEmail());
        values.put(User.getCOLUMN_ID_Country() , user.getID_Country());
        values.put(User.getCOLUMN_ID_DateFormat() , user.getID_DateFormat());
        values.put(User.getCOLUMN_ID_DefaultStyle() , user.getID_DefaultStyle());
        values.put(User.getCOLUMN_ID_Gender() , user.getID_Gender());
        values.put(User.getCOLUMN_ID_TimeDifference() , user.getID_TimeDifference());
        values.put(User.getCOLUMN_ID_WeekBegin() , user.getID_WeekBegin());
        values.put(User.getCOLUMN_LastInteraction() , user.getLastInteraction());
        values.put(User.getCOLUMN_MonthFullName() , user.getMonthFullName());
        values.put(User.getCOLUMN_Password() , user.getPassword());
        values.put(User.getCOLUMN_picture() , user.getPicture());
        values.put(User.getCOLUMN_SignupDate() , user.getSignupDate());
        values.put(User.getCOLUMN_Status() , user.getStatus());
        values.put(User.getCOLUMN_UserName() , user.getUserName());
        values.put(User.getColumnId() , user.getID());
        values.put(User.getColumnIdUser() , user.getID_USER());

        // updating row
        return db.update(User.TABLE_NAME, values, User.COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getID())});
    }


    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(User.TABLE_NAME, User.COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getID())});
        db.close();
    }

    public void deleteAll() {
        String selectQuery = "DELETE FROM " + User.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
    }


    public void deleteAndInsertUser(User user) {

        deleteAll();

        insertUser(user);

    }
}
