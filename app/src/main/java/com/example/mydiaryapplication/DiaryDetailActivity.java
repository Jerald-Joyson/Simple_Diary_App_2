package com.example.mydiaryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class DiaryDetailActivity extends AppCompatActivity {
    EditText contentEditText;
    ImageButton saveNoteBtn,updateNoteBtn;
    TextView pageTitleTextView,dateTextView,deleteNoteTextViewBtn;
    boolean isEditMode = false;
    private String dateString, content;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);

        contentEditText = findViewById(R.id.diary_content_text);
        dateTextView = findViewById(R.id.diary_date_text);
        saveNoteBtn = findViewById(R.id.save_note_btn);
        updateNoteBtn = findViewById(R.id.update_note_btn);
        pageTitleTextView = findViewById(R.id.page_title);
        deleteNoteTextViewBtn = findViewById(R.id.delete_note_text_view_btn);

        DB = new DBHelper(this);


        dateString = getIntent().getStringExtra("date");
        content = getIntent().getStringExtra("content");
        dateTextView.setText(dateString);
        contentEditText.setText(content);

        if (content != null && !content.isEmpty()) {
            isEditMode = true;
        }
        if (isEditMode) {
            saveNoteBtn.setVisibility(View.GONE);
            dateTextView.setText(dateString);
            pageTitleTextView.setText("Edit your Diary");
            updateNoteBtn.setVisibility(View.VISIBLE);
            deleteNoteTextViewBtn.setVisibility(View.VISIBLE);
        }

        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diaryDate = dateTextView.getText().toString();
                String diaryContent = contentEditText.getText().toString();

                if(diaryContent != null && !diaryContent.isEmpty()){
                    Boolean checkInsertData = DB.insertUserData(diaryDate,diaryContent);
                    if (checkInsertData==true){
                        Utility.showToast(DiaryDetailActivity.this, "Diary added successfully");
                        Intent intent = new Intent(DiaryDetailActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Utility.showToast(DiaryDetailActivity.this, "Diary not added");
                    }
                }else{
                    contentEditText.setError("Please enter the contents.");
                    return;
                }
            }
        });

        updateNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diaryDate = dateTextView.getText().toString();
                String diaryContent = contentEditText.getText().toString();
                if(diaryContent != null && !diaryContent.isEmpty()){
                Boolean checkUpdateData = DB.updateUserData(diaryDate,diaryContent);
                if (checkUpdateData==true){
                    Utility.showToast(DiaryDetailActivity.this, "Diary updated successfully");
                    Intent intent = new Intent(DiaryDetailActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Utility.showToast(DiaryDetailActivity.this, "Diary not updated");
                }
                }else{
                    contentEditText.setError("Please enter the contents.");
                    return;
                }
            }
        });

        deleteNoteTextViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diaryDate = dateTextView.getText().toString();
                Boolean checkDeleteData = DB.deleteUserData(diaryDate);
                if (checkDeleteData==true){
                    //contentEditText.setText(" ");
                    Utility.showToast(DiaryDetailActivity.this, "Diary deleted successfully");
                    Intent intent = new Intent(DiaryDetailActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Utility.showToast(DiaryDetailActivity.this, "Diary not deleted");
                }
            }
        });

    }
}