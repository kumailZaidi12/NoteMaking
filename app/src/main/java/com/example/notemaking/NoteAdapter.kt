package com.example.notemaking

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_row.view.*

class NoteAdapter(val arraylist:ArrayList<Notes>) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NoteAdapter.NoteHolder {
        val layoutInfalte=LayoutInflater.from(p0.context)
        val viewInflate=layoutInfalte.inflate(R.layout.item_row,p0,false)

        return NoteHolder(viewInflate)
    }

    override fun getItemCount()= arraylist.size

    override fun onBindViewHolder(holder: NoteAdapter.NoteHolder, positon: Int) {

        val currentNotes=arraylist.get(positon)
        with(holder.itemView){
            tvNote.text=currentNotes.note
            tvTime.text=currentNotes.time
        }


    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


        init {
            itemView.setOnLongClickListener {
               val currentNote=arraylist.get(adapterPosition)

                val database=MyDatabase(itemView.context)
                    arraylist.remove(currentNote)
                notifyDataSetChanged()
                database.delete(currentNote.id.toLong())
                true
            }
        }

     }
}