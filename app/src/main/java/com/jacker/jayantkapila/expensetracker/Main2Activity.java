package com.jacker.jayantkapila.expensetracker;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jacker.jayantkapila.expensetracker.Adapter.TransactionAdapter;
import com.jacker.jayantkapila.expensetracker.Model.Transaction;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class Main2Activity extends AppCompatActivity {

    SharedPreferences pref;

    Button save;
    String mypreferences = "mypref";
    ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
    TransactionAdapter transactionAdapter;
//    Main6Activity o;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        StringBuffer buffer = new StringBuffer();
//        final TextView tv_dateStamp = findViewById(R.id.tv_dateStamp);
//        final CardView dateStamp = findViewById(R.id.dateStamp);


        RecyclerView rv = findViewById(R.id.rv);
//        TextView data2 = (TextView) findViewById(R.id.d2);
        pref = getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
        String l_name = pref.getString("LoggedIn","");
        ExpenseDatabaseHelper helper = new ExpenseDatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor c1 = db.query("user", new String[]{"_id"}, "username = ?", new String[] {l_name},null, null, null);
//        c1.moveToFirst();
//        int l_id = c1.getInt(c1.getColumnIndex("_id"));
//        c1.close();
//        Cursor c2 = db.rawQuery("select _id from expense", new String[] {String.valueOf(l_id)});
//        c2.moveToFirst();
//        int le_id = c2.getInt(c2.getColumnIndex("_id"));
//        c2.close();
        //Cursor cursor1 = db.query("expense", new String[]{"_id","amount", "category"}, null, null, null, null, null);
        Cursor cursor1 = db.query("expense", new String[]{"_id","amount", "category", "date", "time"}, "username = ?", new String[] {l_name},null, null, null);
        //Cursor cursor2 = db.query("user", new String[]{"_id","username", "pass", "email", "Phone", "occupation", "salary", "limits", "balance", "total"}, "username = ?", new String[] {l_name}, null, null, null);
        Cursor cursor2 = db.query("user", new String[]{"_id","username", "pass", "email", "Phone", "occupation", "salary", "limits", "balance", "total"}, "username = ?", new String[] {l_name}, null, null, null);
        int i = 0;
//        transactionList = new List<Transaction>();
        while (cursor1.moveToNext()) {
                String e_id = cursor1.getString(cursor1.getColumnIndex("_id"));
                int a = cursor1.getInt(cursor1.getColumnIndex("amount"));
                String cat = cursor1.getString(cursor1.getColumnIndex("category"));
                String d = cursor1.getString(cursor1.getColumnIndex("date"));
                String t = cursor1.getString(cursor1.getColumnIndex("time"));
                buffer.append("    "+d+"    "+t+"     "+ a + "     " + cat+"\n").toString();
                Transaction transaction = new Transaction(String.valueOf(a), cat, d, t);
                transactionList.add(transaction);
                i++;
        }
        transactionAdapter = new TransactionAdapter(transactionList, this);
        final Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        final Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        rv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if((scrollY - oldScrollY) < 0)
//                {
//                    tv_dateStamp.setVisibility(View.VISIBLE);
//                    dateStamp.setVisibility(View.VISIBLE);
//                    dateStamp.startAnimation(slide_down);
//                    tv_dateStamp.setText("12/12/2002");
//                }
            }
        });
        rv.setAdapter(transactionAdapter);
        transactionAdapter.notifyDataSetChanged();


        //if(cursor1.getString(cursor1.getColumnIndex("category"))!=null)
//            data2.setText(buffer);
        while(cursor2.moveToNext()) {
            String id1 = cursor2.getString(cursor2.getColumnIndex("_id"));
            String uname1 = cursor2.getString(cursor2.getColumnIndex("username"));
            String e1 = cursor2.getString(cursor2.getColumnIndex("email"));
            String phone1 = cursor2.getString(cursor2.getColumnIndex("Phone"));
            String occ1 = cursor2.getString(cursor2.getColumnIndex("occupation"));
            String sala1 = cursor2.getString(cursor2.getColumnIndex("salary"));
            String lim1 = cursor2.getString(cursor2.getColumnIndex("limits"));
            String bal1 = cursor2.getString(cursor2.getColumnIndex("balance"));
            String tot1 = cursor2.getString(cursor2.getColumnIndex("total"));
        }


//            buffer2.append("    "+id + "    " +uname+"    " +e + "    " + phone + "    " + occ+"\n"+sala +"    "+lim+"    "+bal+"    "+tot).toString();
//        salary  = (EditText)findViewById(R.id.salary);
//        limit = (EditText)findViewById(R.id.limit);
////        pref = getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
////        if (pref.getInt("salary_key", NULL) != 0)
////        {
////            salary.setText(pref.getInt("salary_key", NULL));
////            limit.setText(pref.getInt("limit_key", NULL));
////
    }

}