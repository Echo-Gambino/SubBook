package com.echo.subbook;

/**
 * Created by Henry on 2018-01-29.
 */

import java.util.ArrayList;
import java.util.List;

public class SubscriptionList {
    // private ArrayList<Subscription> subList;
    private List<Subscription> subList;
    private int totalCharge;

    public SubscriptionList() {
        this.subList = new ArrayList<Subscription>();
        this.totalCharge = 0;
    }

    public void addSubscription(Subscription subscription) {
        this.totalCharge = this.totalCharge + subscription.getCharge();
        this.subList.add(subscription);
    }

    public void delSubscription(int index) {
        this.totalCharge = this.totalCharge - this.subList.get(index).getCharge();
        this.subList.remove(index);
    }

    public List<Subscription> getSubscriptionList() {
        return this.subList;
    }

    public int getSum() {
        return this.totalCharge;
    }

    public void refreshSum() {
        int sum = 0;
        for(int i = 0; i < this.subList.size(); i++) {
            sum = sum + this.subList.get(i).getCharge();
        }
        this.totalCharge = sum;
    }

    // getSub() <--
    // setSub() <--
    // addSub() <--
    // delSub() <--
    // getSum() <--
    // updateSum() <--


}
