package com.jacker.jayantkapila.expensetracker.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ActionMode;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jacker.jayantkapila.expensetracker.Model.Transaction;
import com.jacker.jayantkapila.expensetracker.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TransactionAdapter extends RecyclerView.Adapter<ViewHolder> {

    List<Transaction> transactionList;
    Transaction transaction;
    Context mContext;

    public TransactionAdapter(List<Transaction> transactionList, Context context) {
//        this.transaction = transaction;
        this.transactionList = transactionList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.passbook_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_spent.setText("₹"+transactionList.get(position).getAmount());
        holder.tv_date.setText(transactionList.get(position).getDate());
        holder.tv_time.setText(transactionList.get(position).getTime());
        String cat[] = {"Food","Entertainment","Travel","Bills","Fees"};
        if ("Food".equals(transactionList.get(position).getCategory()))
        {
            holder.iv.setImageResource(R.drawable.ic_food_black_24dp);
            holder.iv.setBackgroundResource(R.drawable.circlepurple);
        }
        else if ("Entertainment".equals(transactionList.get(position).getCategory()))
        {
            holder.iv.setImageResource(R.drawable.ic_entertainment_black_24dp);
            holder.iv.setBackgroundResource(R.drawable.circlepink);
        }
        else if ("Travel".equals(transactionList.get(position).getCategory()))
        {
            holder.iv.setImageResource(R.drawable.ic_travel_24dp);
            holder.iv.setBackgroundResource(R.drawable.circlegreen);
        }
        else if ("Bills".equals(transactionList.get(position).getCategory()))
        {
            holder.iv.setImageResource(R.drawable.ic_bills_black_24dp);
            holder.iv.setBackgroundResource(R.drawable.circleyellow);
        }
        else if ("Fees".equals(transactionList.get(position).getCategory()))
        {
            holder.iv.setImageResource(R.drawable.ic_attach_money_black_24dp);
            holder.iv.setBackgroundResource(R.drawable.circlepurple);
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

}
class ViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_spent, tv_date, tv_time;
    public CircleImageView iv;
    private ActionMode actionMode = null;

    public ViewHolder(final View itemView) {
        super(itemView);
        tv_spent = itemView.findViewById(R.id.tv_spent);
        tv_date = itemView.findViewById(R.id.tv_date);
        tv_time = itemView.findViewById(R.id.tv_time);
        iv = itemView.findViewById(R.id.iv);
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (actionMode != null)
                {
                    return false;
                }

                actionMode = itemView.startActionMode(callback);
                return true;
            }
        });
    }

    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

            MenuInflater menuInflater = actionMode.getMenuInflater();
            menuInflater.inflate(R.menu.search_menu, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }
    };

}


