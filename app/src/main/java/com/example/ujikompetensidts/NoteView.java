package com.example.ujikompetensidts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class NoteView extends AppCompatActivity {

    private EditText etName, etBornDate, etGender, etAddress;
    private Button btnSave;
    private DatabaseHelper2 dbHelper;
    private Note note;
    private boolean isEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        etName = findViewById(R.id.etName);
        etBornDate = findViewById(R.id.etBornDate);
        etGender = findViewById(R.id.etGender);
        etAddress = findViewById(R.id.etAddress);
        dbHelper = new DatabaseHelper2(this);

        if (getIntent().hasExtra("note_id")) {
            int noteId = getIntent().getIntExtra("note_id", -1);
            note = dbHelper.getNote(noteId);
            if (note != null) {
                etName.setText(note.getName());
                etBornDate.setText(note.getBornDate());
                etGender.setText(note.getGender());
                etAddress.setText(note.getAddress());
                isEdit = true;
            }
        }
    }
}