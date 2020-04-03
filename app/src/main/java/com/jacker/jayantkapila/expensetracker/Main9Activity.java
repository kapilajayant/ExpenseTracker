package com.jacker.jayantkapila.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static java.sql.Types.NULL;

public class Main9Activity extends AppCompatActivity {

    String mypreferences1 = "mypref";
    String tot, bal;
    EditText name2,pass2,email2,contact2,occupation2,income2,limit2;
    ExpenseDatabaseHelper helper = new ExpenseDatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        name2 = (EditText) findViewById(R.id.name1);
        pass2 = (EditText) findViewById(R.id.pass1);
        email2 = (EditText) findViewById(R.id.email1);
        contact2 = (EditText) findViewById(R.id.contact1);
        occupation2 = (EditText) findViewById(R.id.occupation1);
        income2 = (EditText) findViewById(R.id.income1);
        limit2 = (EditText) findViewById(R.id.limit1);
        SQLiteDatabase db1 = helper.getReadableDatabase();
        Cursor cursor2 = db1.query("user", new String[]{"_id", "username", "pass", "email", "Phone", "occupation", "salary", "limits", "balance", "total"}, null, null, null, null, null);
        while (cursor2.moveToNext()) {
            String id = cursor2.getString(cursor2.getColumnIndex("_id"));
            String uname = cursor2.getString(cursor2.getColumnIndex("username"));
            String e = cursor2.getString(cursor2.getColumnIndex("email"));
            String phone = cursor2.getString(cursor2.getColumnIndex("Phone"));
            String occ = cursor2.getString(cursor2.getColumnIndex("occupation"));
            String sala = cursor2.getString(cursor2.getColumnIndex("salary"));
            String lim = cursor2.getString(cursor2.getColumnIndex("limits"));
            bal = cursor2.getString(cursor2.getColumnIndex("balance"));
            tot = cursor2.getString(cursor2.getColumnIndex("total"));
            name2.setText(uname);
            email2.setText(e);
            contact2.setText(phone);
            occupation2.setText(occ);
            income2.setText(sala);
            limit2.setText(lim);
        }
    }

    public void Update(View view) {

            String name1 = name2.getText().toString();
            String pass1 = pass2.getText().toString();
            String email1 = email2.getText().toString();
            String contact1 = contact2.getText().toString();
            String occupation1 = occupation2.getText().toString();
            int income1 = Integer.parseInt(income2.getText().toString());
            int limit1 = Integer.parseInt(limit2.getText().toString());
            SharedPreferences pref = getSharedPreferences(mypreferences1, Context.MODE_MULTI_PROCESS);
            String l_name = pref.getString("LoggedIn", "");
            ExpenseDatabaseHelper helper = new ExpenseDatabaseHelper(this);
//            SQLiteDatabase db1 = helper.getReadableDatabase();
//            Cursor cursor2 = db1.query("user", new String[]{"balance", "total"}, "username = ?", new String[] {l_name}, null, null, null);
//            while(cursor2.moveToNext())
//            {
//                bal = cursor2.getString(cursor2.getColumnIndex("balance"));
//                tot = cursor2.getString(cursor2.getColumnIndex("total"));
//
//            }

            SQLiteDatabase db = helper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("username",name1);
//        cv.put("pass",pass1);
//        cv.put("email",email1);
//        cv.put("Phone",contact1);
//        cv.put("occupation",occupation1);
//        cv.put("salary",income1);
//        cv.put("limits",limit1);
//        cv.put("balance", Integer.parseInt(bal));
//        cv.put("total", Integer.parseInt(tot));
//        db.update("user",cv,  "username ",new String[]{l_name});
        db.execSQL("update user set username = '" + name1 + "', pass = '" + pass1 + "', email = '" + email1 + "' , Phone = '" + contact1 + "', occupation = '" + occupation1 + "', salary = " + income1 + ", limits = " + limit1 +","+"balance = "+bal+", total = "+tot+" where username = '" + l_name + "'");

        SharedPreferences pref1 = getSharedPreferences(mypreferences1, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref1.edit();
        editor.remove("LoggedIn");
        editor.apply();
        Intent i1 = new Intent(Main9Activity.this, Main6Activity.class);
        startActivity(i1);
        }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        finishActivity(1);
//        startActivity(new Intent(this, MainActivity.class));
//    }
}