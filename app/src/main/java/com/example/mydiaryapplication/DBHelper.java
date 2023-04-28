package com.example.mydiaryapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context,"Userdiary.db" , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Diarydetails(date TEXT primary key,content TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Diarydetails");
    }

    public Boolean insertUserData(String date,String content){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date",date);
        contentValues.put("content",content);
        long result = DB.insert("Diarydetails",null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean updateUserData(String date,String content){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content",content);
        Cursor cursor = DB.rawQuery("Select * from Diarydetails where date = ?",new String[]{date});
        if (cursor.getCount()>0){

        long result = DB.update("Diarydetails",contentValues,"date=?",new String[]{date});
        if (result == -1){
            return false;
        }else{
            return true;
        }

        }else{
            return false;
        }
    }

    public Boolean deleteUserData(String date){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Diarydetails where date = ?",new String[]{date});
        if (cursor.getCount()>0){

            long result = DB.delete("Diarydetails","date=?",new String[]{date});
            if (result == -1){
                return false;
            }else{
                return true;
            }

        }else{
            return false;
        }
    }

    public Cursor getUserData(String date){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Diarydetails where date = ?",new String[]{date});
        return cursor;
    }
}
