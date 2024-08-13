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
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;

public class AddEditNote extends AppCompatActivity {

    private EditText etName, etBornDate, etGender, etAddress;
    private Button btnSave;
    private DatabaseHelper2 dbHelper;
    private Note note;
    private EditText tanggalEditText;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        tanggalEditText = findViewById(R.id.etBornDate);
        etName = findViewById(R.id.etName);
        etBornDate = findViewById(R.id.etBornDate);
        etGender = findViewById(R.id.etGender);
        etAddress = findViewById(R.id.etAddress);
        btnSave = findViewById(R.id.btnSave);
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
        tanggalEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

        private void showDatePickerDialog () {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                            tanggalEditText.setText(selectedDate);
                        }
                    },
                    year, month, day);
            datePickerDialog.show();


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nama = etName.getText().toString().trim();
                    String ttl = etBornDate.getText().toString().trim();
                    String jkelamin = etGender.getText().toString().trim();
                    String alamat = etAddress.getText().toString().trim();
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                            Locale.getDefault()).format(new Date());
                    if (nama.isEmpty() || ttl.isEmpty()) {
                        Toast.makeText(AddEditNote.this, "Please fill all fields",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isEdit) {
                        note.setName(nama);
                        note.setBornDate(ttl);
                        note.setGender(jkelamin);
                        note.setAddress(alamat);
                        note.setDate(date);
                        dbHelper.updateNote(note);
                    } else {
                        note = new Note();
                        note.setName(nama);
                        note.setBornDate(ttl);
                        note.setGender(jkelamin);
                        note.setAddress(alamat);
                        note.setDate(date);
                        dbHelper.addNote(note);

                    }
                    finish();
                }
            });

        }
    }
