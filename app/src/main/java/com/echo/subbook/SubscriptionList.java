package com.echo.subbook;

/**
 * Created by Henry on 2018-01-29.
 */

import android.widget.ArrayAdapter;

import java.util.ArrayList;
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

    public void addSubscription(Subscription subscription) {
        subArray.add(subscription);
    }

    public void remSubscription(Subscription subscription) {
        subArray.remove(subscription);
    }

    public int getSum() {
        return this.totalCharge;
    }
//    public void setSubscription() {
//        // nothing
//    }

}
