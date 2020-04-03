package com.jacker.jayantkapila.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Main4Activity extends AppCompatActivity {


    public String s_uid;
    public String s_name;
    public String s_email;
    public String s_contact;
    public String s_occupation;
    public String s_income;
    public String s_balance;
    public String s_total;
    public ImageView profile;
    SharedPreferences pref;
    String mypreferences = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);
//        TextView uid = (TextView)findViewById(R.id.uid);
        TextView name = (TextView)findViewById(R.id.name);
        TextView email = (TextView)findViewById(R.id.email);
        TextView contact = (TextView)findViewById(R.id.contact);
        TextView occupation = (TextView)findViewById(R.id.occupation);
        TextView income = (TextView)findViewById(R.id.income);
        TextView profile_balance = (TextView)findViewById(R.id.profile_balance);
        TextView profile_spent = (TextView)findViewById(R.id.profile_spent);
        //ImageView profile_image = (ImageView)findViewById(R.id.profileimage);

        pref = getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
        String l_name = pref.getString("LoggedIn","");
        ExpenseDatabaseHelper helper = new ExpenseDatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("user",new String[] {"username","username","email","Phone","occupation","salary","balance","total"},"username = ?",new String[]{ l_name },null,null,null);
        cursor.moveToFirst();

        s_uid = cursor.getString(cursor.getColumnIndex("username"));
        s_name = cursor.getString(cursor.getColumnIndex("username"));
        s_email = cursor.getString(cursor.getColumnIndex("email"));
        s_contact = cursor.getString(cursor.getColumnIndex("Phone"));
        s_occupation = cursor.getString(cursor.getColumnIndex("occupation"));
        s_income = cursor.getString(cursor.getColumnIndex("salary"));
        s_balance = cursor.getString(cursor.getColumnIndex("balance"));
        s_total = cursor.getString(cursor.getColumnIndex("total"));
        //uid.setText(s_uid);
        name.setText(s_name);
        email.setText(s_email);
        contact.setText(s_contact);
        occupation.setText(s_occupation);
        income.setText(s_income);
        profile_balance.setText(s_balance);
        profile_spent.setText(s_total);
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        finishActivity(1);
//        startActivity(new Intent(this, MainActivity.class));
//    }
}
