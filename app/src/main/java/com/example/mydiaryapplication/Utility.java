package com.example.mydiaryapplication;

import android.content.Context;
import android.widget.Toast;

public class Utility {
    static void showToast(Context context,String Msg){
        Toast.makeText(context,Msg,Toast.LENGTH_SHORT).show();
    }
}
