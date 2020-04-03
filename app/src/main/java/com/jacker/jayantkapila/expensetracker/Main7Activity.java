package com.jacker.jayantkapila.expensetracker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.sql.Date;
import java.util.Calendar;

import static java.sql.Types.NULL;
import static java.sql.Types.TIME;

public class Main7Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private DatabaseReference myRef;

    RadioGroup radioGroup;
    RadioButton radioButton;
    int p1,n;
   // String expense="500";
    String balance;
    int salary,limit;
    String mypreferences1 = "mypref";
    String add_category;
    TextView text_food, text_entertainment, text_travel, text_bills, text_fees;
    TextView txt_date, txt_time;
    LinearLayout ll_food, ll_entertainment, ll_travel, ll_bills, ll_fees;
    CardView card_food, card_entertainment, card_travel, card_bills, card_fees;

    String Final_Date, Final_Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        TextView text_food = (TextView)findViewById(R.id.text_food);
        TextView text_entertainment = (TextView)findViewById(R.id.text_entertainment);
        TextView text_travel = (TextView)findViewById(R.id.text_travel);
        TextView text_bills = (TextView)findViewById(R.id.text_bills);
        TextView text_fees = (TextView)findViewById(R.id.text_fees);

        txt_date = findViewById(R.id.txt_date);
        txt_time = findViewById(R.id.txt_time);

        ll_food = findViewById(R.id.ll_food);
        ll_entertainment = findViewById(R.id.ll_entertainment);
        ll_travel = findViewById(R.id.ll_travel);
        ll_bills = findViewById(R.id.ll_bills);
        ll_fees = findViewById(R.id.ll_fees);

        card_food = findViewById(R.id.card_food);
        card_entertainment= findViewById(R.id.card_entertainment);
        card_travel = findViewById(R.id.card_travel);
        card_bills = findViewById(R.id.card_bills);
        card_fees = findViewById(R.id.card_fees);


        ImageButton date_button = findViewById(R.id.date_button);
        ImageButton time_button = findViewById(R.id.time_button);

        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int mYear, mMonth, mDay;

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Main7Activity.this, Main7Activity.this, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.setCancelable(false);
            }
        });

        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mHour, mMinute;

                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(Main7Activity.this, Main7Activity.this, mHour, mMinute, false);
                timePickerDialog.show();
                timePickerDialog.setCancelable(false);

            }
        });
        SharedPreferences pref = getSharedPreferences(mypreferences1, Context.MODE_MULTI_PROCESS);

        final String l_name = pref.getString("LoggedIn","");
        Button addbutton = (Button)findViewById(R.id.add_button);
       // Getting the limit value
//        limit = pref.getString("limit_key","");
//        n2 = Integer.parseInt(limit);
        ExpenseDatabaseHelper helper = new ExpenseDatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("user", new String[] {"salary", "limits"},"username = ?", new String[] {l_name},null, null, null);
        cursor.moveToFirst();
        limit = cursor.getInt(cursor.getColumnIndex("limits"));
        salary = cursor.getInt(cursor.getColumnIndex("salary"));
        cursor.close();
        db.close();
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                EditText amount1 = (EditText)findViewById(R.id.amount);
                //SharedPreferences pref1=getSharedPreferences(mypreferences1,Context.MODE_MULTI_PROCESS);
                //SharedPreferences.Editor editor = pref1.edit();
//                radioGroup = findViewById(R.id.radiogroup);
//                int radioId = radioGroup.getCheckedRadioButtonId();
//                radioButton = findViewById(radioId);


                try {
//                    if (!(radioButton.getText().toString().isEmpty()) && !(amount1.getText().toString().isEmpty()))
                    if (!(add_category.isEmpty()) && !(amount1.getText().toString().isEmpty()))
                    {
                        if (n > limit) {
                            Toast.makeText(getBaseContext(), "Spend within your limit:-" + limit, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
//                            int radioId = radioGroup.getCheckedRadioButtonId();
//                            radioButton = findViewById(radioId);
                            String expense = amount1.getText().toString();
                            n = Integer.parseInt(expense);
                            ExpenseDatabaseHelper helper1 = new ExpenseDatabaseHelper(Main7Activity.this);
                            SQLiteDatabase db1 = helper1.getWritableDatabase();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("amount", n);
                            contentValues.put("category", add_category);
                            contentValues.put("date",Final_Date);
                            contentValues.put("time",Final_Time);
                            contentValues.put("username", l_name);

                            db1.insert("expense", null, contentValues);

                            myRef = FirebaseDatabase.getInstance().getReference("Expenses");
                            String s_id = myRef.push().getKey();
                            myRef.child(s_id).child("amount").setValue(n);
                            myRef.child(s_id).child("category").setValue(add_category);
                            myRef.child(s_id).child("date").setValue(Final_Date);
                            myRef.child(s_id).child("time").setValue(Final_Time);
                            myRef.child(s_id).child("username").setValue(l_name);



                            Intent i1 = new Intent(Main7Activity.this, MainActivity.class);
                            //editor.putInt("balance_key",amount);
                            //i1.putExtra("spent_key",n);
                            //editor.putInt("spent_key",n);
                            //editor.commit();
                            Toast.makeText(getBaseContext(), "Your expense is: " + n, Toast.LENGTH_SHORT).show();
                            Toast.makeText(getBaseContext(), "Selected Category: " + add_category, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getBaseContext(), "Selected Radio Button: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
                            startActivity(i1);
                        }
                    }
                }
                catch (NullPointerException e)
                {
                    Toast.makeText(getBaseContext(), "Please select details properly", Toast.LENGTH_SHORT).show();
                }
                }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        int fYear, fMonth, fDay;
        fYear = year;
        fMonth = month + 1;
        fDay = dayOfMonth;

        Final_Date = fDay+"-"+fMonth+"-"+fYear;

        txt_date.setText(Final_Date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        int fHour = hourOfDay, fMinute = minute;

        Final_Time = fHour+":"+fMinute;

        txt_time.setText(Final_Time);
    }

    public void add_food(View view)
    {
        card_food.setCardBackgroundColor(getResources().getColor(R.color.gray));
        ll_food.setBackgroundColor(getResources().getColor(R.color.gray));


        card_fees.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_fees.setBackgroundColor(getResources().getColor(R.color.white));
        card_travel.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_travel.setBackgroundColor(getResources().getColor(R.color.white));
        card_bills.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_bills.setBackgroundColor(getResources().getColor(R.color.white));
        card_entertainment.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_entertainment.setBackgroundColor(getResources().getColor(R.color.white));
        //if (card_food.getCardBackgroundColor().equals(getResources().getColor(R.color.gray)) && ll_food.getBackground().equals(getResources().getColor(R.color.gray)))
        //if (card_food.getCardBackgroundColor().toString().equals((getResources().getColor(R.color.gray))))
//        if(add_category.equals("Food"))
//        {
//            add_category = "";
//            card_food.setCardBackgroundColor(getResources().getColor(R.color.white));
//            ll_food.setBackgroundColor(getResources().getColor(R.color.white));
//        }
        add_category = "Food";//text_food.getText().toString();
//        else
//        {
//            card_food.setCardBackgroundColor(getResources().getColor(R.color.gray));
//            ll_food.setBackgroundColor(getResources().getColor(R.color.gray));
//        }
    }
    public void add_entertainment(View view)
    {
        card_entertainment.setCardBackgroundColor(getResources().getColor(R.color.gray));
        ll_entertainment.setBackgroundColor(getResources().getColor(R.color.gray));

        card_fees.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_fees.setBackgroundColor(getResources().getColor(R.color.white));
        card_travel.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_travel.setBackgroundColor(getResources().getColor(R.color.white));
        card_bills.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_bills.setBackgroundColor(getResources().getColor(R.color.white));
        card_food.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_food.setBackgroundColor(getResources().getColor(R.color.white));

        //if (card_entertainment.getCardBackgroundColor().equals(getResources().getColor(R.color.gray)) && ll_entertainment.getBackground().equals(getResources().getColor(R.color.gray)))
        //if (card_entertainment.getCardBackgroundColor().toString().equals(getResources().getColor(R.color.gray)))
//        if(add_category.equals("Entertainment"))
//        {
//            add_category = "";
//            card_entertainment.setCardBackgroundColor(getResources().getColor(R.color.white));
//            ll_entertainment.setBackgroundColor(getResources().getColor(R.color.white));
//        }
        add_category = "Entertainment";//text_entertainment.getText().toString();

//        else
//        {
//            card_entertainment.setCardBackgroundColor(getResources().getColor(R.color.gray));
//            ll_entertainment.setBackgroundColor(getResources().getColor(R.color.gray));
//        }
    }
    public void add_travel(View view)
    {
        ll_travel.setBackgroundColor(getResources().getColor(R.color.gray));
        card_travel.setCardBackgroundColor(getResources().getColor(R.color.gray));

        card_fees.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_fees.setBackgroundColor(getResources().getColor(R.color.white));
        card_bills.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_bills.setBackgroundColor(getResources().getColor(R.color.white));
        card_entertainment.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_entertainment.setBackgroundColor(getResources().getColor(R.color.white));
        card_food.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_food.setBackgroundColor(getResources().getColor(R.color.white));

        //if (card_travel.getCardBackgroundColor().equals(getResources().getColor(R.color.gray)) && ll_travel.getBackground().equals(getResources().getColor(R.color.gray)))
        //if (card_travel.getCardBackgroundColor().toString().equals(getResources().getColor(R.color.gray)))
//        if(add_category.equals("Travel"))
//        {
//            add_category = "";
//            card_travel.setCardBackgroundColor(getResources().getColor(R.color.white));
//            ll_travel.setBackgroundColor(getResources().getColor(R.color.white));
//        }
        add_category = "Travel";//text_travel.getText().toString();
//        else
//        {
//            card_travel.setCardBackgroundColor(getResources().getColor(R.color.gray));
//            ll_travel.setBackgroundColor(getResources().getColor(R.color.gray));
//        }
    }
    public void add_bills(View view)
    {
        card_bills.setCardBackgroundColor(getResources().getColor(R.color.gray));
        ll_bills.setBackgroundColor(getResources().getColor(R.color.gray));
        //if (card_bills.getCardBackgroundColor().equals(getResources().getColor(R.color.gray)) && ll_bills.getBackground().equals(getResources().getColor(R.color.gray)))
        //if (card_bills.getCardBackgroundColor().toString().equals(getResources().getColor(R.color.gray)))
        card_fees.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_fees.setBackgroundColor(getResources().getColor(R.color.white));
        card_travel.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_travel.setBackgroundColor(getResources().getColor(R.color.white));
        card_entertainment.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_entertainment.setBackgroundColor(getResources().getColor(R.color.white));
        card_food.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_food.setBackgroundColor(getResources().getColor(R.color.white));

//        if(add_category.equals("Bills"))
//        {
//            add_category = "";
//            card_bills.setCardBackgroundColor(getResources().getColor(R.color.white));
//            ll_bills.setBackgroundColor(getResources().getColor(R.color.white));
//        }
        add_category = "Bills";//text_bills.getText().toString();

//        else
//        {
//            card_bills.setCardBackgroundColor(getResources().getColor(R.color.gray));
//            ll_bills.setBackgroundColor(getResources().getColor(R.color.gray));
//        }
    }
    public void add_fees(View view)
    {
        card_fees.setCardBackgroundColor(getResources().getColor(R.color.gray));
        ll_fees.setBackgroundColor(getResources().getColor(R.color.gray));


        card_bills.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_bills.setBackgroundColor(getResources().getColor(R.color.white));
        card_travel.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_travel.setBackgroundColor(getResources().getColor(R.color.white));
        card_entertainment.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_entertainment.setBackgroundColor(getResources().getColor(R.color.white));
        card_food.setCardBackgroundColor(getResources().getColor(R.color.white));
        ll_food.setBackgroundColor(getResources().getColor(R.color.white));
        //if (card_fees.getCardBackgroundColor().equals(getResources().getColor(R.color.gray)) && ll_fees.getBackground().equals(getResources().getColor(R.color.gray)))
        //if (card_fees.getCardBackgroundColor().toString().equals(getResources().getColor(R.color.gray)))
//        if(add_category.equals("Fees"))
//        {
//            add_category = "";
//            card_fees.setCardBackgroundColor(getResources().getColor(R.color.white));
//            ll_fees.setBackgroundColor(getResources().getColor(R.color.white));
//        }
        add_category = "Fees";//text_fees.getText().toString();
//        else
//        {
//            card_fees.setCardBackgroundColor(getResources().getColor(R.color.white));
//            ll_fees.setBackgroundColor(getResources().getColor(R.color.white));
//        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        finishActivity(1);
    }
}