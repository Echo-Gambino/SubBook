package com.echo.subbook;

/**
 * Created by Henry on 2018-01-29.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Subscription {
    private String name;        // Have a maximum of 20 characters in name.
    private Date date;
    private double charge;
    private String comment;     // Have a maximum of 30 characters in comments.

    public Subscription() {
        this.name = "";
        this.date = new Date();
        this.charge = 0.00;
        this.comment = "";
    }

    public Subscription(String name) {
        this();
        this.name = name;
    }

    public Subscription(String name, Date date) {
        this(name);
        this.date = date;
    }

    public Subscription(String name, Date date, double charge) {
        this(name, date);
        this.charge = charge;
    }

    public Subscription(String name, Date date, double charge, String comment) {
        this(name, date, charge);
        this.comment = comment;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) throws StringTooLongException {
        /* if/else statements taken from Tweet.java in Echo-Gambino/lonelyTwitter,
        which was forked from vingk/lonelyTwitter.
        */
        if (name.length() > 20) {
            throw new StringTooLongException();
        } else {
            this.name = name;
        }
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) {
        String date_format = "EEE MMM dd HH:mm:ss zzz yyyy";
        try {
            this.date = new SimpleDateFormat(date_format).parse(date);
        } catch (Exception e) {}
    }

    public double getCharge() {
        return this.charge;
    }

    public void setCharge(double charge) throws NegativeIntegerException {
        if (charge < 0.0) {
            throw new NegativeIntegerException();
        } else {
            this.charge = charge;
        }
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) throws StringTooLongException {
        /* if/else statements taken from Tweet.java in Echo-Gambino/lonelyTwitter,
        which was forked from vingk/lonelyTwitter.
        */
        if (comment.length() > 30) {
            throw new StringTooLongException();
        } else {
            this.comment = comment;
        }
    }

    @Override
    public String toString() {
        String message = name + ": \t $" + Double.toString(charge) + "\n"
                + date.toString();
        return message;
    }

}




