package com.example.flashcardapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "FlashcardDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE cards(id INTEGER PRIMARY KEY AUTOINCREMENT, question TEXT, answer TEXT, category TEXT)");    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertCard(String q, String a, String c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("question", q);
        cv.put("answer", a);
        cv.put("category", c);
        db.insert("cards", null, cv);
    }

    public void deleteCard(String question) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cards", "question=?", new String[]{question});
    }

    public void updateCard(String oldQ, String newQ, String newA, String newC) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("question", newQ);
        cv.put("answer", newA);
        cv.put("category", newC);

        db.update("cards", cv, "question=?", new String[]{oldQ});
    }


    public ArrayList<Flashcard> getAllCards() {
        ArrayList<Flashcard> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM cards", null);

        while (c.moveToNext()) {
            String q = c.getString(1);
            String a = c.getString(2);
            String cat = c.getString(3);
            list.add(new Flashcard(q, a, cat));
        }
        return list;
    }
}