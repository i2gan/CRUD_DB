package com.abc.crudsqliteii;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mans.db";
    private static final String TABLE_NAME = "mans_table";
    private static final String COL_ID = "_id";
    private static final String COL_NAME = "name";
    private static final String COL_SURNAME = "surname";
    private static final String COL_YEAR = "year";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_SURNAME + " TEXT, " +
                COL_YEAR + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addOne(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, data.name);
        cv.put(COL_SURNAME, data.surname);
        cv.put(COL_YEAR, data.year);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1)
            return false;
        else
            return true;
    }

    public LinkedList<Data> getAll() {
        LinkedList<Data> list = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null,
                null, null, null);

        if (cursor.moveToFirst())
            do {
                int id_n = cursor.getColumnIndex(COL_NAME);
                int id_s = cursor.getColumnIndex(COL_SURNAME);
                int id_y = cursor.getColumnIndex(COL_YEAR);

                Data data = new Data(cursor.getString(id_n),
                        cursor.getString(id_s), cursor.getInt(id_y));
                list.add(data);
            } while (cursor.moveToNext());
        db.close();
        return list;
    }

    public boolean updData(String id, String name, String surname, String year) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_YEAR, id);
        cv.put(COL_NAME, name);
        cv.put(COL_SURNAME, surname);
        cv.put(COL_YEAR, year);
        db.update(TABLE_NAME, cv, COL_ID + " =?", new String[] {id});
        return true;
    }

    public void delOne(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID+" = ?", new String[]{id});
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}