package com.example.notemaking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    val notesList = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database = MyDatabase(this)
        val noteAdapter = NoteAdapter(notesList)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = noteAdapter

        btn.setOnClickListener {
            val notes = Notes(
                et.text.toString(),
                DateFormat.getDateTimeInstance().format(Date())
            )
            val add = notesList.add(notes)
           noteAdapter.notifyDataSetChanged()
            val long = database.insert(notes)
        }

        notesList.addAll(database.getAllNotes())
    }
}
