package com.test.mekanishampracticaltask

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.test.mekanishampracticaltask.adapters.NotesAdapter
import com.test.mekanishampracticaltask.databinding.ActivityMainBinding
import com.test.mekanishampracticaltask.models.Notes
import com.test.mekanishampracticaltask.utils.Constants
import com.test.mekanishampracticaltask.utils.getRandomColor
import com.test.mekanishampracticaltask.utils.gone
import com.test.mekanishampracticaltask.utils.visible
import com.test.mekanishampracticaltask.viewModels.NotesViewModel

class MainActivity : AppCompatActivity() {
    private val notesViewModel: NotesViewModel by viewModels()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val title = data?.getStringExtra(Constants.TITLE)
            val description = data?.getStringExtra(Constants.DESCRIPTION)
            val isFor = data?.getStringExtra(Constants.IS_FOR)
            val id = data?.getLongExtra(Constants.ID, 0)
            val color = data?.getIntExtra(Constants.COLOR, 0)
            if (isFor == Constants.EDIT) {
                notesViewModel.editNote(
                    Notes(
                        note = "$title",
                        description = description,
                        color = color,
                        id = id
                    )
                )
            } else {
                notesViewModel.addNote(
                    Notes(
                        note = "$title",
                        description = description,
                        color = color,
                        id = id
                    )
                )
            }
        }
    }

    private val notesAdapter by lazy {
        NotesAdapter(
            onDeleteClicked = {
                notesViewModel.deleteNote(it)
            },

            onEditClick = {
                //please note that i can do this data passing of the model directly using gson or the kotlixserialization i already have used it in different many projects i have extentiopns for that also but since as per mentioned i cant use any existing code of mine or ai i passed currently this as a sinlgle single string because if i goto setup whole thing with dependency and extention functions then it will take too much time
                Intent(this@MainActivity, AddEditNoteActivity::class.java).apply {
                    putExtra(Constants.IS_FOR, Constants.EDIT)
                    putExtra(Constants.TITLE, it.note)
                    putExtra(Constants.DESCRIPTION, it.description)
                    putExtra(Constants.COLOR, it.color)
                    putExtra(Constants.ID, it.id)
                    startForResult.launch(this)
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
        observeData()
    }

    private fun init() {
        binding.apply {
            rvNotes.adapter = notesAdapter
            btnAddNote.setOnClickListener {
                Intent(this@MainActivity, AddEditNoteActivity::class.java).apply {
                    putExtra(Constants.IS_FOR, Constants.ADD)
                    startForResult.launch(this)
                }
            }

            btnSearch.setOnClickListener {
                searchBar.visible()
            }

            searchBar.addTextChangedListener {
                //we can do this searching in the backgound thread also using the debounce and flow but as of now im doing it with basic implementation
                if (it.toString().isNotEmpty()) {
                    val filteredList = notesViewModel.notesList.value?.filter { notes ->
                        notes.note?.contains(it.toString(),true) == true
                    }
                    notesAdapter.updateList(filteredList ?: listOf())
                }
            }
        }
    }

    private fun observeData() {
        notesViewModel.notesList.observe(this@MainActivity) {
            if (it.isNotEmpty()) {
                binding.apply {
                    rvNotes.visible()
                    tvNoNotes.gone()
                    notesAdapter.updateList(it)
                }
            } else {
                binding.apply {
                    rvNotes.gone()
                    tvNoNotes.visible()
                    notesAdapter.clearList()
                }
            }
        }
    }
}