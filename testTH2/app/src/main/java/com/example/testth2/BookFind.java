package com.example.testth2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

    String nameJob;
    EditText nameJobEdit;
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


        nameJobEdit = view.findViewById(R.id.nameJob);
        findBtn = view.findViewById(R.id.findBtn);
        records = view.findViewById(R.id.sizeRecord);

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameJobEdit.getText().toString().trim())) {
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                }
                else {

                    nameJob = nameJobEdit.getText().toString();
                    mBookList = mBookDAO.searchByName(nameJob);
                    records.setText("Tìm thấy " + mBookList.size() + " bản ghi:");
                    Book.sortBooksByDate(mBookList);
                    bookListAdapter.setBookList(mBookList);

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
