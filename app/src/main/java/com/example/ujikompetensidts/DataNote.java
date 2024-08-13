package com.example.ujikompetensidts;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import
        com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
public class DataNote extends AppCompatActivity {
    private ListView listView;
    private EditText etSearch;
    private DatabaseHelper2 dbHelper;
    private List<Note> notesList;
    private ArrayAdapter<String> adapter;
    private List<String> titlesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_note);

        listView = findViewById(R.id.listView);
        etSearch = findViewById(R.id.etSearch);
        dbHelper = new DatabaseHelper2(this);
        notesList = new ArrayList<>();
        titlesList = new ArrayList<>();
        final AlertDialog.Builder builder = new AlertDialog.Builder(DataNote.this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                Intent intent = new Intent(DataNote.this,
                        NoteView.class);
                intent.putExtra("note_id", notesList.get(position).getId());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                builder.setTitle("Pilihan");

                String[] options = {"Lihat Data", "Update Data", "Hapus Data"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent intent = new Intent(DataNote.this, NoteView.class);
                                intent.putExtra("note_id", notesList.get(position).getId());
                                startActivity(intent);
                        }
                        switch (which){
                            case 1:
                                Intent intent = new Intent(DataNote.this, AddEditNote.class);
                                intent.putExtra("note_id", notesList.get(position).getId());
                                startActivity(intent);
                        }
                        switch (which){
                            case 2:
                                dbHelper.deleteNote(notesList.get(position));
                                loadNotes();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i,
                                          int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int
                    i1, int i2) {
                searchNotes(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
    private void loadNotes() {
        notesList = dbHelper.getAllNotes();
        titlesList.clear();
        for (Note note : notesList) {
            titlesList.add(note.getName()+ "\n" + note.getDate());
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, titlesList);
        listView.setAdapter(adapter);
    }
    private void searchNotes(String keyword) {
        notesList = dbHelper.searchNotes(keyword);
        titlesList.clear();
        for (Note note : notesList) {
            titlesList.add(note.getName());
        }
        adapter.notifyDataSetChanged();
    }
}