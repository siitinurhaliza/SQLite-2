package com.example.sitinurhaliza.uts.sqlite2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mahasiswa.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MAHASISWA = "mahasiswa";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAMA = "nama";
    private static final String COLUMN_NIM = "nim";
    private static final String COLUMN_PRODI = "prodi";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MAHASISWA_TABLE = "CREATE TABLE " + TABLE_MAHASISWA + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAMA + " TEXT,"
                + COLUMN_NIM + " TEXT,"
                + COLUMN_PRODI + " TEXT"
                + ")";
        db.execSQL(CREATE_MAHASISWA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAHASISWA);
        onCreate(db);
    }


    // Create
    public void addMahasiswa(Mahasiswa mahasiswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, mahasiswa.getNama());
        values.put(COLUMN_NIM, mahasiswa.getNim());
        values.put(COLUMN_PRODI, mahasiswa.getProdi());

        db.insert(TABLE_MAHASISWA, null, values);
        db.close();
    }

    // Read
    public List<Mahasiswa> getAllMahasiswa() {
        List<Mahasiswa> mahasiswaList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MAHASISWA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                mahasiswa.setNama(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA)));
                mahasiswa.setNim(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM)));
                mahasiswa.setProdi(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODI)));
                mahasiswaList.add(mahasiswa);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return mahasiswaList;
    }

    // Update
    public int updateMahasiswa(Mahasiswa mahasiswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, mahasiswa.getNama());
        values.put(COLUMN_NIM, mahasiswa.getNim());
        values.put(COLUMN_PRODI, mahasiswa.getProdi());

        // updating row
        return db.update(TABLE_MAHASISWA, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(mahasiswa.getId())});
    }

    // Delete
    public void deleteMahasiswa(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MAHASISWA, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}