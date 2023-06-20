package com.db_sqlite.UserInterface

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.db_sqlite.R
import com.db_sqlite.SQLiteDatabase.DatabaseHelper
import com.db_sqlite.UserInterface.ActivityMain.Companion.cvBack
import com.db_sqlite.UserInterface.ActivityMain.Companion.tvTitle

class FragmentSecond(val adapterPosition: Int?) : Fragment() {

    private lateinit var et_position: EditText
    private lateinit var et_id: EditText
    private lateinit var et_name: EditText
    private lateinit var et_description: EditText
    private lateinit var cv_edit: CardView
    private lateinit var tv_edit: TextView

    lateinit var model: ArrayList<Model>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tvTitle.text = "Edit object"
        cvBack.visibility = View.VISIBLE
        cvBack.setOnClickListener { requireActivity().onBackPressed() }

        et_position = requireView().findViewById(R.id.et_position)
        et_id = requireView().findViewById(R.id.et_id)
        et_name = requireView().findViewById(R.id.et_name)
        et_description = requireView().findViewById(R.id.et_description)
        cv_edit = requireView().findViewById(R.id.cv_edit)
        tv_edit = requireView().findViewById(R.id.tv_edit)

        val databaseHelper = DatabaseHelper(context)
        val db = databaseHelper.writableDatabase
        @SuppressLint("Recycle") val c =
            db.rawQuery("SELECT * FROM test WHERE position = '$adapterPosition'", null)
        c.moveToNext()
        et_position.setText(c.getString(c.getColumnIndex("position")))
        et_id.setText(c.getString(c.getColumnIndex("id")))
        et_name.setText(c.getString(c.getColumnIndex("name")))
        et_description.setText(c.getString(c.getColumnIndex("description")))

        cv_edit.setOnClickListener {
            val databaseHelper = DatabaseHelper(context)
            val db = databaseHelper.writableDatabase

            databaseHelper.updateData(et_position.text.toString().toInt(), et_id.text.toString(), et_name.text.toString(), et_description.text.toString())

            db.close()
            requireActivity().onBackPressed()
        }
        et_position.addTextChangedListener(etWatcher)
        et_id.addTextChangedListener(etWatcher)
        et_name.addTextChangedListener(etWatcher)
    }

    private val etWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            cv_edit.isEnabled = false
            cv_edit.setCardBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.system_accent1_50
                )
            )
            tv_edit.setTextColor(ContextCompat.getColor(context!!, R.color.system_accent1_100))
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val positionInput = et_position.text.toString().trim { it <= ' ' }
            val idInput = et_id.text.toString().trim { it <= ' ' }
            val nameInput = et_name.text.toString().trim { it <= ' ' }
            if (positionInput.isNotEmpty() && idInput.isNotEmpty() && nameInput.isNotEmpty()) {
                cv_edit.isEnabled = true
                cv_edit.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.system_accent1_300
                    )
                )
                tv_edit.setTextColor(ContextCompat.getColor(context!!, R.color.black))
            }
        }

        override fun afterTextChanged(s: Editable) {}
    }
}