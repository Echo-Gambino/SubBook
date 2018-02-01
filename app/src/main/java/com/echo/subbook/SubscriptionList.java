package com.echo.subbook;

/**
 * Created by Henry on 2018-01-29.
 */

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubscriptionList {
    // private ArrayList<Subscription> subList;
    private ArrayList<Subscription> subArray;
    private int totalCharge;

    public SubscriptionList() {
        this.subArray = new ArrayList<Subscription>();
        this.totalCharge = 0;
    }

    public ArrayList<Subscription> getSubscriptionList() {
        return this.subArray;
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

    public void addSubscription(Subscription subscription) {
        this.totalCharge = this.totalCharge + subscription.getCharge();
        subArray.add(subscription);
    }

    public void remSubscription(Subscription subscription) {
        this.totalCharge = this.totalCharge - subscription.getCharge();
        subArray.remove(subscription);
    }

    public int getSum() {
        return this.totalCharge;
    }


}
