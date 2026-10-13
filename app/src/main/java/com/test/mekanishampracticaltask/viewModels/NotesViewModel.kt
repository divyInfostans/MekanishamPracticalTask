package com.test.mekanishampracticaltask.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.mekanishampracticaltask.models.Notes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotesViewModel : ViewModel() {

    //same way as adapter i kept it simple here also state flow use is good for the data flow but something is missing in syntax due to that im not getting updated data in the collect so i skipped that for now and used the live data also live data is bets ofr the xml while state flow is best for the compose codes.
    private val _notesList: MutableLiveData<List<Notes>> = MutableLiveData(listOf())
    val notesList: LiveData<List<Notes>> = _notesList

    private val listOfNotes = mutableListOf<Notes>()

    fun addNote(note: Notes) {
        listOfNotes.add(note)
        _notesList.value = listOfNotes
    }

    fun deleteNote(notes: Notes){
        listOfNotes.remove(notes)
        _notesList.value = listOfNotes
    }

    fun editNote(notes: Notes){
        val oldNote = listOfNotes.find { it.id == notes.id }
        listOfNotes.remove(oldNote)
        listOfNotes.add(notes)
        _notesList.value = listOfNotes
    }
}