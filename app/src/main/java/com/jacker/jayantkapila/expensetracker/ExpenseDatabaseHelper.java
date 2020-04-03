package com.jacker.jayantkapila.expensetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by JayantKapila on 27-Jun-18.
 */

public class ExpenseDatabaseHelper extends SQLiteOpenHelper {

    public ExpenseDatabaseHelper(Context context) {
        super(context, "jacker", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table expense(_id integer primary key autoincrement, username text, amount integer, category text, date text, time text)");
        sqLiteDatabase.execSQL("create table user(_id integer primary key autoincrement, username text, pass text, email text, Phone text, occupation text, salary integer, limits integer, balance integer, total integer, foreign key(username) references expense(username))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists user");
        sqLiteDatabase.execSQL("drop table if exists expense");
        onCreate(sqLiteDatabase);
    }
}
