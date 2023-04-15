package com.example.testth2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    int id;
    String name, status, releaseDate, content, cor;
    private Spinner mStatusSpinner;
    private TextView mId;
    private EditText mNameEditText;
    private EditText mReleaseDateEditText;
    private EditText mContentEditText;
    private RadioButton mCanhan;
    private RadioButton mNhom;

    private Button buttonDate;
    private Button buttonChange;
    private Button buttonDelete;
    private Button buttonBack;
    private BookDAO mBookDAO;

    public static void selectSpinnerItemByValue(Spinner spnr, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if (adapter.getItem(position).equals(value)) {
                spnr.setSelection(position);
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");
        id = book.getId();
        name = book.getName();
        status = book.getStatus();
        releaseDate = book.getDateRelease();
        content = book.getContent();
        cor = book.getCor();


        mStatusSpinner = findViewById(R.id.spn_status2);
        mId = findViewById(R.id.editId);
        mNameEditText = findViewById(R.id.editTextName);
        mReleaseDateEditText = findViewById(R.id.editTextReleaseDate);
        mContentEditText = findViewById(R.id.editContentJob);
        mCanhan = findViewById(R.id.canhanRadio);
        mNhom = findViewById(R.id.nhomRadio);
        buttonDate = findViewById(R.id.buttonDate);
        buttonChange = findViewById(R.id.buttonChange);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonBack = findViewById(R.id.buttonBack);

        mBookDAO = new BookDAO(this);

        mId.setText(Integer.toString(id));
        mNameEditText.setText(name);
        mContentEditText.setText(content);
        mReleaseDateEditText.setText(releaseDate);
        selectSpinnerItemByValue(mStatusSpinner, status);
        if (cor.equals("cá nhân")) {
            mCanhan.setChecked(true);
//            mNhom.setChecked(false);
        } else {
//            mCanhan.setChecked(false);
            mNhom.setChecked(true);
        }

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog datePickerDialog = new DatePickerDialog(BookDetailActivity.this, (view, year1, monthOfYear, dayOfMonth) -> {
                    // Đặt ngày được chọn vào EditText
                    String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                    mReleaseDateEditText.setText(selectedDate);
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idd = Integer.parseInt(mId.getText().toString());
                String named = mNameEditText.getText().toString();

                String content = mContentEditText.getText().toString();
                String releaseDate = mReleaseDateEditText.getText().toString();
                String status = mStatusSpinner.getSelectedItem().toString();
                String cor = (mNhom.isChecked()) ? ("nhom") : ("ca nhan");

                Book book = new Book (idd, named, content, releaseDate, status, cor);
                mBookDAO.updateBook(book);
                Toast.makeText(BookDetailActivity.this, "Bản ghi số " + idd + " đã được cập nhật", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBookDAO.deleteBook(book);
                Toast.makeText(BookDetailActivity.this, "Bản ghi số " + book.getId()+ " đã bị xoá", Toast.LENGTH_SHORT).show();
                onBackPressed();
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