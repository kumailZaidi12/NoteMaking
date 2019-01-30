package com.example.notemaking

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.ArrayAdapter

class MyDatabase(context: Context) : SQLiteOpenHelper(context, "my_app.db", null, 3) {
    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(
            """
            CREATE TABLE notestable(
            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
           note TEXT NOT NULL,
           time TEXT NOT NULL);
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun getAllNotes(): ArrayList<Notes> {

        val noteList = ArrayList<Notes>()

        val noteCursor = readableDatabase.query(
            "notestable", null, null, null,
            null, null, null
        )

        while (noteCursor.moveToNext()) {
            val id = noteCursor.getInt(0)
            val note = noteCursor.getString(1)
            val time = noteCursor.getString(2)

            val currentNote = Notes(note = note, time = time, id = id)

            noteList.add(currentNote)

        }

        noteCursor.close()

        return noteList
    }

    fun insert(notes: Notes): Long {
        //insert the student object in the database
        val contentValues = ContentValues()

        with(contentValues) {
            put("note", notes.note)
            put("time", notes.time)
        }

        return writableDatabase.insert("notestable", null, contentValues)
    }

    fun getNoteById(id: Long): Notes? {


        val cursorNote = readableDatabase.query("notestable", null, "id=?", arrayOf(id.toString()), null, null, null);

        if (cursorNote.moveToNext()) {
            val id = cursorNote.getInt(0)
            val note = cursorNote.getString(1)
            val time = cursorNote.getString(2)


            return Notes(note, time, id)

        }

        cursorNote.close()

        return null

    }

    fun delete(id: Long) {
        writableDatabase.delete("notestable", "id=?", arrayOf(id.toString()))
    }


}