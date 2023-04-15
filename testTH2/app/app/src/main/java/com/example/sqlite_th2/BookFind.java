package com.example.sqlite_th2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookFind extends Fragment {

    RecyclerView bookListRecycleView;
    BookListAdapter bookListAdapter;
    List<Book> mBookList;
    private BookDAO mBookDAO;

    Double startPrice, endPrice;
    EditText startEdt, endEdt;
    Button findBtn;

    TextView records;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_find, container, false);

        mBookDAO = new BookDAO(getContext());
        mBookList = mBookDAO.getAllBooks();

        bookListRecycleView = view.findViewById(R.id.book_find_list);
        bookListRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookListAdapter = new BookListAdapter(getContext(), mBookList);
        bookListRecycleView.setAdapter(bookListAdapter);

        bookListAdapter.setOnItemClickListener(new BookListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                Log.d("Tuan", "onItemClick: " + book.toString());
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });


        startEdt = view.findViewById(R.id.from);
        endEdt = view.findViewById(R.id.to);
        findBtn = view.findViewById(R.id.findBtn);
        records = view.findViewById(R.id.sizeRecord);

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(startEdt.getText().toString().trim()) && (TextUtils.isEmpty(endEdt.getText().toString().trim()))) {
                    mBookList = mBookDAO.getAllBooks();
                    records.setText("Tất cả bản ghi:");
                    bookListAdapter.setBookList(mBookList);
                }
                else if (TextUtils.isEmpty(startEdt.getText().toString().trim()) || (TextUtils.isEmpty(endEdt.getText().toString().trim())))
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                else {
                    startPrice = Double.parseDouble(startEdt.getText().toString().trim());
                    endPrice = Double.parseDouble(endEdt.getText().toString().trim());
                    if (startPrice < 0 || endPrice  < 0) {
                        Toast.makeText(getContext(), "Nhập số tiền dương", Toast.LENGTH_SHORT).show();
                    } else if (startPrice > endPrice) {
                        Toast.makeText(getContext(), "Giá sau phải cao hơn giá trước", Toast.LENGTH_SHORT).show();
                    } else {
                        mBookList = mBookDAO.searchByPrice(startPrice, endPrice);
                        records.setText("Tìm thấy " + mBookList.size() + " bản ghi:");
                        Book.sortBooksByPriceDescending(mBookList);
                        bookListAdapter.setBookList(mBookList);
                    }
                }

            }
        });





        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mBookList = mBookDAO.getAllBooks();
        bookListAdapter.setBookList(mBookList);
    }
}
