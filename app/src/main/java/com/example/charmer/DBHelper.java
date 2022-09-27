package com.example.charmer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DNNAME="login.db";
    public DBHelper(Context context) {
        super(context, "login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(id INTEGER primary key AUTOINCREMENT NOT NULL, mail TEXT, password TEXT, name TEXT, phone TEXT, ubi TEXT, sexo TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists users");
    }

    public Boolean insertData ( String mail , String password, String name, String phone, String ubi, String sexo ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(" mail ", mail);
        values.put(" password ", password);
        values.put(" name ", name);
        values.put(" phone ", phone);
        values.put(" ubi ", ubi);
        values.put(" sexo ", sexo);
        long result = db.insert(" users ", null, values );
        if (result == -1) return false;
        else
            return true;
    }
    public Boolean checkusername ( String username ) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select * from users where mail = ? ", new String[]{username} );
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Boolean checkusernamepassword ( String username , String password ) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select * from users where mail = ? and password = ? ", new String[]{username, password} )        ;
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public String[] MostrarUsuario(String correo){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE mail = ?", new String[]{correo});
        return new String[]{cursor.getString(3),cursor.getString(1),cursor.getString(2),cursor.getString(4),cursor.getString(5),cursor.getString(6)};
    }
}
