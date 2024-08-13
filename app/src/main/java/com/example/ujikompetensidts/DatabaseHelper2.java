package com.example.ujikompetensidts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper2 extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes.db";
    private static final String TABLE_NAME = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "nama";
    private static final String COLUMN_BORNDATE = "ttl";
    private static final String COLUMN_GENDER = "jkelamin";
    private static final String COLUMN_ADDRESS = "alamat";
    private static final String COLUMN_DATE = "date";
    public DatabaseHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_BORNDATE + " TEXT, "
                + COLUMN_GENDER + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_DATE + " TEXT)";
        db.execSQL(createTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, note.getName());
        values.put(COLUMN_BORNDATE, note.getBornDate());
        values.put(COLUMN_GENDER, note.getGender());
        values.put(COLUMN_ADDRESS, note.getAddress());
        values.put(COLUMN_DATE, note.getDate());
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }
    public Note getNote(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_BORNDATE, COLUMN_GENDER, COLUMN_ADDRESS,
                        COLUMN_DATE},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BORNDATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
        cursor.close();
        return note;
    }
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " +
                COLUMN_DATE + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                note.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                note.setBornDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BORNDATE)));
                note.setGender(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)));
                note.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
                note.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, note.getName());
        values.put(COLUMN_BORNDATE, note.getBornDate());
        values.put(COLUMN_GENDER, note.getGender());
        values.put(COLUMN_ADDRESS, note.getAddress());
        values.put(COLUMN_DATE, note.getDate());
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }
    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
    public List<Note> searchNotes(String keyword) {
        List<Note> notes = new ArrayList<>();
        String searchQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_NAME + " LIKE ? OR " + COLUMN_BORNDATE + " LIKE ? ORDER BY " +
                COLUMN_DATE + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, new String[]{"%" + keyword
                + "%", "%" + keyword + "%"});
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                note.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                note.setBornDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BORNDATE)));
                note.setGender(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)));
                note.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
                note.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));

                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }
}
