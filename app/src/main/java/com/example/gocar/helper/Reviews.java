package com.example.gocar.helper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gocar.R;

public class Reviews {

    //private int UniqueId;
    private String userid;
    private int carid;
    private String UserReview;

    public Reviews(String userid, int carid, String UserReview) {
        this.userid = userid;
        this.carid = carid;
        this.UserReview = UserReview;

    }
    public Reviews(String UserReview) {
        this.UserReview = UserReview;
    }

    public String getuserid() {
        return userid;
    }

    public int getcarid() {
        return carid;
    }

    public String getUserReview() {
        return UserReview;
    }
}

