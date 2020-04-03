package com.jacker.jayantkapila.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Main5Activity extends AppCompatActivity {

    SharedPreferences pref;
    String mypreferences = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        String cat[] = {"Food","Entertainment","Travel","Bills","Fees"};

        pref = getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
        String l_name = pref.getString("LoggedIn","");
        ExpenseDatabaseHelper helper = new ExpenseDatabaseHelper(this);

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor1 = db.query("expense", new String[]{"amount"},"category = ? and username = ?", new String[]{"Food", l_name},null, null,null);
        //Cursor cursor1 = db.query("expense", new String[]{"amount", "category"},null,null,null, null,null);
        //Cursor cursor2 = db.rawQuery("select amount from expense",);
        Cursor cursor2 = db.query("expense", new String[]{"amount"},"category = ? and username = ?", new String[]{"Entertainment", l_name},null, null,null);
        Cursor cursor3 = db.query("expense", new String[]{"amount"},"category = ? and username = ?", new String[]{"Travel", l_name},null, null,null);
        Cursor cursor4 = db.query("expense", new String[]{"amount"},"category = ? and username = ?", new String[]{"Bills", l_name},null, null,null);
        Cursor cursor5 = db.query("expense", new String[]{"amount"},"category = ? and username = ?", new String[]{"Fees", l_name},null, null,null);

        int f = 0, e = 0, t = 0, b = 0, fe = 0;

        cursor1.moveToFirst();
        while(!cursor1.isAfterLast())
        {
            f += cursor1.getInt(cursor1.getColumnIndex("amount"));
            cursor1.moveToNext();
        }
        cursor1.close();

        cursor2.moveToFirst();
        while(!cursor2.isAfterLast())
        {
            e += cursor2.getInt(cursor2.getColumnIndex("amount"));
            cursor2.moveToNext();
        }
        cursor2.close();

        cursor3.moveToFirst();
        while(!cursor3.isAfterLast())
        {
            t += cursor3.getInt(cursor3.getColumnIndex("amount"));
            cursor3.moveToNext();
        }
        cursor3.close();

        cursor4.moveToFirst();
        while(!cursor4.isAfterLast())
        {
            b += cursor4.getInt(cursor4.getColumnIndex("amount"));
            cursor4.moveToNext();
        }
        cursor4.close();

        cursor5.moveToFirst();
        while(!cursor5.isAfterLast())
        {
            fe += cursor5.getInt(cursor5.getColumnIndex("amount"));
            cursor5.moveToNext();
        }
        cursor5.close();
        //int expense[] = {200,300,400,500,1000};
        int expense[] = {f,e,t,b,fe};
        PieChart pie = findViewById(R.id.pie);
        Description description = new Description();
        description.setText("Color Coded Report");
        pie.setDescription(description);
        List<PieEntry> pieEntries1 = new ArrayList<>();
        for (int i = 0;i<expense.length;i++)
            pieEntries1.add(new PieEntry(expense[i],cat[i]));
        PieDataSet pieDataSet = new PieDataSet(pieEntries1,"<----Expenditure");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pie.setData(pieData);
        pie.animateY(1000);
        pie.invalidate();
        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        finishActivity(1);
//        startActivity(new Intent(this, MainActivity.class));
//    }
}