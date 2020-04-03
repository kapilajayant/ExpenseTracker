package com.jacker.jayantkapila.expensetracker;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int total;
    int n_balance, n_spent,sp;
    String mypreferences1 = "mypref";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView balance = (TextView) findViewById(R.id.balance);
        TextView hi  = (TextView)findViewById(R.id.hi);
        TextView expenditure = (TextView) findViewById(R.id.expenditure);
        CardView salary_card = (CardView) findViewById(R.id.salary_id);
        CardView add_card = (CardView) findViewById(R.id.add_id);
        CardView profile_card = (CardView) findViewById(R.id.profile_id);
        CardView reports_card = (CardView) findViewById(R.id.reports_id);
        SharedPreferences pref = getSharedPreferences(mypreferences1, Context.MODE_MULTI_PROCESS);

        String l_name = pref.getString("LoggedIn","");
        //DATABASE HELPER CLASS OBJECT..............................................................
        ExpenseDatabaseHelper helper = new ExpenseDatabaseHelper(this);

        salary_card.setOnClickListener(this);
        add_card.setOnClickListener(this);
        profile_card.setOnClickListener(this);
        reports_card.setOnClickListener(this);

        //FETCHING THE USERNAME FOR THE WELCOME LABEL...............................................

        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor c1 = db.query("user",new String[] {"username"},"username = ?",new String[]{ l_name },null,null,null);
//        c1.moveToFirst();
//        String un ="";
//            un =  c1.getString(c1.getColumnIndex("username"));
//        c1.close();
        hi.setText("Hi, "+l_name+"!");

        //..........................................................................................

        //GETTING THE EXPENDITURE BROUGHT FROM Main7Activity........................................

        Cursor cursor = db.query("expense",new String[] {"amount"}, "username = ?",new String[]{ l_name },null,null,null);

        //Cursor cursor = db.query("expense",new String[] {"amount"}, null,null,null,null,null);
//        if(cursor.isNull(0))
//        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                n_spent += cursor.getInt(0);
                cursor.moveToNext();
            }
        cursor.close();

        //..........................................................................................
        //}
//
//        n_balance = pref.getInt("salary",NULL);
//
//        if(savedInstanceState != null)
//        {
//            sp = savedInstanceState.getInt("total_spent");
//            n_balance = savedInstanceState.getInt("balance");
//        }
//
//        if (pref.getInt("spent_key", NULL) != 0)
//            n_spent = pref.getInt("spent_key", NULL);
//        else
//            n_spent = 0;

//        Bundle bundle = getIntent().getExtras();
//        if (bundle!=null)
//        {
//            n_spent = bundle.getInt("spent_key");
//        }
        //else
          //  n_spent = 0;
    //    System.out.println("salary is :-"+n_spent);
        //sp += n_spent;
  //      n_balance = pref.getInt("salary_key", NULL);

        //FETCHING THE SALARY AND CALCULATING BALANCE...............................................

        Cursor cursor1 = db.query("user",new String[] {"salary"},"username = ?",new String[]{ l_name }, null, null, null);
        //Cursor cursor1 = db.query("user",new String[] {"salary"},null, null, null, null, null);
//        if(!cursor1.isNull(0))
//        {
        cursor1.moveToFirst();
        while(!cursor1.isAfterLast()) {
            n_balance = cursor1.getInt(0) - n_spent;
            cursor1.moveToNext();
        }
        cursor1.close();

        //..........................................................................................
        //}
//        SQLiteDatabase db1 = helper.getReadableDatabase();
//        Cursor cursor2 = db.query("user",new String[] {"username"},null,null,null,null,null);
//        cursor2.moveToFirst();
//        String s = cursor2.getString(cursor2.getColumnIndex("username"));
//        if(s!=null)
//        {
//            SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
//            sqLiteDatabase.execSQL("update user set balance = " + n_balance + ", total=" + n_spent + "where username = " + cursor1.getString(cursor1.getColumnIndex("username")));
//        }
        balance.setText(String.valueOf(n_balance));
        expenditure.setText(String.valueOf(n_spent));

        //UPDATING THE BALANCE AND TOTAL COLUMNS OF THE USER TABLE..................................

        SQLiteDatabase db1 = helper.getWritableDatabase();
        db1.execSQL("UPDATE USER SET balance = "+n_balance+", total = "+n_spent+" where username = '"+l_name+"'");

        //..........................................................................................
    }
//
//    @SuppressLint("MissingSuperCall")
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        savedInstanceState.putInt("total_spent",sp);
//        savedInstanceState.putInt("balance",n_balance);
//    }
    //        Bundle e = getIntent().getExtras();
//        Bundle e1 = getIntent().getExtras();
//        if(e==null)
//        {
//            return;
//        }
//       salary = e.getInt("k1");
//       limit1 = e.getInt("k2");
//       n5 = e1.getInt("k3");
//         Main7Activity o1 = new Main7Activity();
//
//            o1.pref1 = getSharedPreferences(o1.mypreferences1, Context.MODE_PRIVATE);
//            n5 = o1.pref1.getInt(o1.balance_key, NULL);

//            ///savedsalary = salary;
//            Toast.makeText(getBaseContext(), "Spent " + n5, Toast.LENGTH_SHORT).show();
//            //int n6 = savedsalary -n5;
//            String v = String.valueOf(n5);
//        balance.setText(v);
//        expenditure.setText(String.valueOf(n5));
    public void onClick(View view)
    {
        Intent i;
        switch (view.getId())
        {
            case R.id.salary_id :i = new Intent(this,Main2Activity.class);startActivity(i);break;
            case R.id.add_id : i=new Intent(this,Main7Activity.class);startActivity(i);break;
            case R.id.profile_id : i=new Intent(this,Main4Activity.class);startActivity(i);break;
            case R.id.reports_id : i=new Intent(this,Main5Activity.class);startActivity(i);break;
            default:break;
        }
    }
    public void UpdateDataActivity(View view)
    {
        Toast.makeText(getBaseContext(),"Currently under Development",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,Main9Activity.class);
        startActivity(i);
    }

    public void logout(View view)
    {
        SharedPreferences pref = getSharedPreferences(mypreferences1, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("LoggedIn");
        editor.commit();
        Intent i = new Intent(this, Main6Activity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure you wannna exit? This will log you out...")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences pref = getSharedPreferences(mypreferences1, Context.MODE_MULTI_PROCESS);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.remove("LoggedIn");
                        editor.apply();
                        Intent i1 = new Intent(MainActivity.this, Main6Activity.class);
                        startActivity(i1);
                    }
                })
                .setNegativeButton("No", null);
        builder.setTitle("Exit Window");
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finishActivity(1);
    }
}