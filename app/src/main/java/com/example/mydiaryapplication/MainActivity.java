package com.example.mydiaryapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ImageButton menuBtn;
    Button writeBtn;
    DBHelper DB;
    private String stringDateSelected;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calendarView);
        writeBtn = findViewById(R.id.writeBtn);
        menuBtn = findViewById(R.id.menu_btn);

        DB = new DBHelper(this);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                stringDateSelected = Integer.toString(i2) + "-" + Integer.toString(i1 + 1) + "-" + Integer.toString(i);
                Cursor res = DB.getUserData(stringDateSelected);
                if (res.moveToFirst()) {
                    int nameIndex = res.getColumnIndex("content");
                    String diaryData = res.getString(nameIndex);

                    Intent intent = new Intent(MainActivity.this, DiaryDetailActivity.class);
                    intent.putExtra("date", stringDateSelected);
                    intent.putExtra("content", diaryData);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, DiaryDetailActivity.class);
                    intent.putExtra("date", stringDateSelected);
                    intent.putExtra("content", "");
                    startActivity(intent);
                }
            }
        });
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateString = todaysDate();
                Cursor res = DB.getUserData(dateString);
                if (res.moveToFirst()) {
                    int nameIndex = res.getColumnIndex("content");
                    String diaryData = res.getString(nameIndex);

                    Intent intent = new Intent(MainActivity.this, DiaryDetailActivity.class);
                    intent.putExtra("date", dateString);
                    intent.putExtra("content", diaryData);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, DiaryDetailActivity.class);
                    intent.putExtra("date", dateString);
                    intent.putExtra("content", "");
                    startActivity(intent);
                }
            }
        });
        menuBtn.setOnClickListener((v) -> showMenu());
    }

    void showMenu() {
        //Disply Menu
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, menuBtn);
        popupMenu.getMenu().add("EXIT");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle() == "EXIT") {
                    finish();
                    System.exit(0);
                    return true;
                }
                return false;
            }
        });
    }

    public String todaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String stringDate = Integer.toString(day) + "-" + Integer.toString(month) + "-" + Integer.toString(year);
        return stringDate;
    }
}