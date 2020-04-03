package com.jacker.jayantkapila.expensetracker.Model;

public class Transaction {

    String amount, category, date, time;

    public Transaction(String amount, String category, String date, String time) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
