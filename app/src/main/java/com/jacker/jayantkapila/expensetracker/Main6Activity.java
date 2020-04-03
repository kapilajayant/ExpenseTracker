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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.sql.Types.NULL;

public class Main6Activity extends AppCompatActivity {

    public String s_password;
    public String s_username;
    String mypreferences1 = "mypref";
    SharedPreferences pref1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        pref1 = getSharedPreferences(mypreferences1, Context.MODE_PRIVATE);

//        if ((pref1.getString("LoggedIn", "")).length() == 0) {

            Button login = (Button) findViewById(R.id.login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText username = (EditText) findViewById(R.id.username);
                    EditText password = (EditText) findViewById(R.id.password);
                    s_username = username.getText().toString();
                    s_password = password.getText().toString();
                    String u_username;
                    String u_password;
                    ExpenseDatabaseHelper helper = new ExpenseDatabaseHelper(Main6Activity.this);
                    SQLiteDatabase db = helper.getReadableDatabase();
                    Cursor cursor2 = db.query("user", new String[]{"username", "pass"}, null, null, null, null, null);
                    cursor2.moveToFirst();
                    while (!cursor2.isAfterLast()) {
                        u_username = cursor2.getString(cursor2.getColumnIndex("username"));
                        u_password = cursor2.getString(cursor2.getColumnIndex("pass"));

                        if (s_username.equals(u_username)) {
                            if (s_password.equals(u_password)) {
                                SharedPreferences.Editor editor = pref1.edit();
                                editor.putString("LoggedIn", u_username);
                                editor.commit();
                                finishActivity(1);
                                Intent I = new Intent(Main6Activity.this, MainActivity.class);
                                startActivity(I);
                                username.setText("");
                                password.setText("");
                            } else
                                Toast.makeText(getBaseContext(), "Oops! Incorrect password", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getBaseContext(), "No Account Exists with this Username", Toast.LENGTH_SHORT).show();
                        cursor2.moveToNext();
                    }
                    cursor2.close();
//                    }
//                    else
//                    {
//                        Toast.makeText(getBaseContext(),"Sorry Wrong Password",Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else
//                {
//                    Toast.makeText(getBaseContext(),"Sorry Wrong username",Toast.LENGTH_SHORT).show();
//                }
                }
            });
        }
//        else {
//
//            startActivity(new Intent(Main6Activity.this, MainActivity.class));
//            finish();
//        }
//    }
    public void SignUpActivity(View view)
    {
        Intent i = new Intent(this,Main8Activity.class);
        startActivity(i);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finishActivity(1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity(1);
    }
}