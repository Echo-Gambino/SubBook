package com.echo.subbook;

/**
 * Created by Henry on 2018-01-29.
 */

import java.util.Date;

public class Subscription {
    private String name;        // Have a maximum of 20 characters in name.
    private Date date;
    private int charge;
    private String comment;     // Have a maximum of 30 characters in comments.

    public Subscription() {
        this.name = "";
        this.date = new Date();
        this.charge = 0;
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

    public Subscription(String name, Date date, int charge) {
        this(name, date);
        this.charge = charge;
    }

    public Subscription(String name, Date date, int charge, String comment) {
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

    public int getCharge() {
        return this.charge;
    }

    public void setCharge(int charge) throws NegativeIntegerException {
        if (charge < 0) {
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
        String message = name + ": \t $" + Integer.toString(charge) + "\n"
                + date.toString();
        return message;
    }

}




