package com.example.testth2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class BookAddActivity extends AppCompatActivity {

    private Spinner mStatusSpinner;
    private EditText mNameEditText;
    private EditText mContentEditText;
    private EditText mReleaseDateEditText;
    private RadioGroup mCorRadioGroup;
    private RadioButton mCanhan;
    private RadioButton mNhom;
    private Button buttonDate;
    private Button buttonSave;
    private Button buttonBack;
    private BookDAO mBookDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add);

        mStatusSpinner = findViewById(R.id.editStatusAdd);
        mNameEditText = findViewById(R.id.editTextNameAdd);
        mReleaseDateEditText = findViewById(R.id.editTextReleaseDateAdd);
        mCorRadioGroup = findViewById(R.id.corRadioAdd);
        mNhom = findViewById(R.id.nhomRadioAdd);
        mCanhan = findViewById(R.id.canhanRadioAdd);
        mContentEditText = findViewById(R.id.editContentJobAdd);

        buttonDate = findViewById(R.id.buttonDate);
        buttonSave = findViewById(R.id.buttonSave);
        buttonBack = findViewById(R.id.buttonBack);
        mBookDAO = new BookDAO(this);

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog datePickerDialog = new DatePickerDialog(BookAddActivity.this, (view, year1, monthOfYear, dayOfMonth) -> {
                    // Đặt ngày được chọn vào EditText
                    String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                    mReleaseDateEditText.setText(selectedDate);
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEditText.getText().toString().trim();
                String content = mContentEditText.getText().toString().trim();
                String releaseDate = mReleaseDateEditText.getText().toString().trim();
                String status = (String) mStatusSpinner.getSelectedItem();
                String cor = (mNhom.isChecked()) ? ("nhom") : ("ca nhan");

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(content) || TextUtils.isEmpty(releaseDate) ||
                        TextUtils.isEmpty(status) || TextUtils.isEmpty(cor))
                    Toast.makeText(BookAddActivity.this, "Hãy điền đầy đủ các mục", Toast.LENGTH_SHORT).show();
                else {
                    Book book = new Book(name, status, content, releaseDate, cor);
                    Log.d("Tuan", "onClick: " + name + "\n" + content + "\n" + status + "\n" + cor);
                    mBookDAO.addBook(book);
                    Toast.makeText(BookAddActivity.this, "Thêm thành công sách " + name, Toast.LENGTH_SHORT).show();
                    mNameEditText.setText(null);
                    mContentEditText.setText(null);
                    mReleaseDateEditText.setText(null);
//                    mCorEditText.setText(null);

                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        List<String> publishers = Arrays.asList(getResources().getStringArray(R.array.publishers_array));
        PublisherAdapter adapter = new PublisherAdapter(this, android.R.layout.simple_spinner_item, publishers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatusSpinner.setAdapter(adapter);
    }



}
