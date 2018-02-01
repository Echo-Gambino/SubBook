package com.echo.subbook;

/**
 * Created by Henry on 2018-01-29.
 */

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubscriptionList {
    private ArrayList<Subscription> subList;
    private double totalCharge;

    public SubscriptionList() {
        this.subList = new ArrayList<Subscription>();
        this.totalCharge = 0.0;
    }

    public void addSubscription(Subscription subscription) {
        this.totalCharge = this.totalCharge + subscription.getCharge();
        this.subList.add(subscription);
    }

    public void delSubscription(int index) {
        this.totalCharge = this.totalCharge - this.subList.get(index).getCharge();
        this.subList.remove(index);
    }

    public ArrayList<Subscription> getSubscriptionList() {
        return this.subList;
    }

    public void setSubscription(Subscription old_subscription, Subscription new_subscription) {
        String new_name = new_subscription.getName();
        try {
            old_subscription.setName(new_name);
        } catch(Exception e) {}     // Do nothing if new_name exceeds char count.

        Date new_date = new_subscription.getDate();
        old_subscription.setDate(new_date);

        int new_charge = new_subscription.getCharge();
        try {
            old_subscription.setCharge(new_charge);
        } catch(Exception e) {}

        String new_comment = new_subscription.getComment();
        try {
            new_subscription.setComment(new_comment);
        } catch(Exception e) {}     // Do nothing if new_comment exceeds char count.

    }

    public double getSum() {
        return this.totalCharge;
    }

    public void refreshSum() {
        double sum = 0.0;
        for(int i = 0; i < this.subList.size(); i++) {
            sum = sum + this.subList.get(i).getCharge();
        }
        this.totalCharge = sum;
    }

}
