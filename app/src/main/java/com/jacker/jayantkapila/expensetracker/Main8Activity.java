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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main8Activity extends AppCompatActivity {


    private DatabaseReference myRef1;

    String mypreferences1 = "mypref";
    SharedPreferences pref1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
    }

    public void SignUp(View view)
    {
        EditText name = (EditText)findViewById(R.id.name);
        EditText pass = (EditText)findViewById(R.id.pass);
        EditText email = (EditText)findViewById(R.id.email);
        EditText contact = (EditText)findViewById(R.id.contact);
        EditText occupation = (EditText)findViewById(R.id.occupation);
        EditText income = (EditText)findViewById(R.id.income);
        EditText limit= (EditText)findViewById(R.id.limit);
        //_id integer, username text, email text, Phone integer, occupation text, salary integer, limits integer, balance integer, total integer
        ExpenseDatabaseHelper helper = new ExpenseDatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username",name.getText().toString());
        cv.put("pass",pass.getText().toString());
        cv.put("email",email.getText().toString());
        cv.put("Phone",contact.getText().toString());
        cv.put("occupation",occupation.getText().toString());
        cv.put("salary",Integer.parseInt(income.getText().toString()));
        cv.put("limits",Integer.parseInt(limit.getText().toString()));
        cv.put("balance",Integer.parseInt(income.getText().toString()));
        cv.put("total", 0);
        db.insert("user",null, cv);
//        ContentValues cv1 = new ContentValues();
//        cv1.put("username",name.getText().toString());
//        db.insert("expense", null, cv1);
//        db.close();

        //Cursor c2 = db1.query("user", new String[] {"_id"},"username = ?",new String[]{name.getText().toString()},null,null,null);
        pref1=getSharedPreferences(mypreferences1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref1.edit();
        editor.putString("LoggedIn",name.getText().toString());
        editor.commit();

        myRef1 = FirebaseDatabase.getInstance().getReference("Users");
        String s_id = myRef1.push().getKey();
        myRef1.child(s_id).child("username").setValue(name.getText().toString());
        myRef1.child(s_id).child("pass").setValue(pass.getText().toString());
        myRef1.child(s_id).child("email").setValue(email.getText().toString());
        myRef1.child(s_id).child("Phone").setValue(contact.getText().toString());
        myRef1.child(s_id).child("occupation").setValue(occupation.getText().toString());
        myRef1.child(s_id).child("salary").setValue(income.getText().toString());
        myRef1.child(s_id).child("limits").setValue(limit.getText().toString());
        myRef1.child(s_id).child("balance").setValue(income.getText().toString());
        myRef1.child(s_id).child("total").setValue(0);

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        finishActivity(1);
//        startActivity(new Intent(this, Main6Activity.class));
//    }
}
