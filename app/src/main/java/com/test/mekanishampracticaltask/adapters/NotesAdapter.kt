package com.test.mekanishampracticaltask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.test.mekanishampracticaltask.databinding.ItemNotesBinding
import com.test.mekanishampracticaltask.models.Notes

//commet for the viewer : i can do this with using diffutil but as time is running out i have just kep it as a simple adapter

class NotesAdapter(
    private val onDeleteClicked: (Notes) -> Unit,
    private val onEditClick: (Notes) -> Unit
) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    private val listOfNotes = mutableListOf<Notes>()

    inner class ViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(notes: Notes) {
            binding.apply {
                notes.apply {
                    tvNote.text = note
                    color?.let {
                        root.setCardBackgroundColor(
                            ContextCompat.getColor(
                                root.context,
                                it
                            )
                        )
                    }
                    btnDelete.setOnClickListener {
                        onDeleteClicked.invoke(this)
                    }
                    btnEdit.setOnClickListener {
                        onEditClick.invoke(this)
                    }
                }
            }
        }
    }

    fun updateList(list: List<Notes>) {
        listOfNotes.clear()
        listOfNotes.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList() {
        listOfNotes.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(listOfNotes[position])
    }

    override fun getItemCount(): Int {
        return listOfNotes.size
    }
}