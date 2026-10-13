package com.test.mekanishampracticaltask

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.test.mekanishampracticaltask.databinding.ActivityAddEditNoteBinding
import com.test.mekanishampracticaltask.utils.Constants
import com.test.mekanishampracticaltask.utils.getRandomColor

class AddEditNoteActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAddEditNoteBinding.inflate(layoutInflater)
    }

    private val isFor by lazy {
        intent.getStringExtra(Constants.IS_FOR)
    }


    private val color by lazy {
        intent.getIntExtra(Constants.COLOR, 0)
    }


    private val id by lazy {
        intent.getLongExtra(Constants.ID, 0)
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
    }

    private fun init() {
        binding.apply {

            if (isFor == Constants.EDIT) {
                edtTitle.setText(intent.getStringExtra(Constants.TITLE))
                edtDescription.setText(intent.getStringExtra(Constants.DESCRIPTION))
            }

            btnSave.setOnClickListener {
                if (edtTitle.text.isNullOrBlank()) {
                    edtTitle.error = "Please enter the title"
                } else if (edtDescription.text.isNullOrBlank()) {
                    edtDescription.error = "Please enter the description"
                } else {
                    val dataIntent = if (isFor == Constants.EDIT) {
                        Intent().apply {
                            putExtra(Constants.TITLE, edtTitle.text.toString())
                            putExtra(Constants.DESCRIPTION, edtDescription.text.toString())
                            putExtra(Constants.COLOR, color)
                            putExtra(Constants.ID, id)
                            putExtra(Constants.IS_FOR, isFor)
                        }
                    } else {
                        Intent().apply {
                            putExtra(Constants.TITLE, edtTitle.text.toString())
                            putExtra(Constants.DESCRIPTION, edtDescription.text.toString())
                            putExtra(Constants.COLOR, getRandomColor())
                            putExtra(Constants.ID, System.currentTimeMillis())
                            putExtra(Constants.IS_FOR, isFor)
                        }
                    }

                    setResult(Activity.RESULT_OK, dataIntent)
                    finish()
                }
            }

            btnBack.setOnClickListener {
                finish()
            }
        }
    }
}