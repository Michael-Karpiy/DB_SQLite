package com.db_sqlite.UserInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.db_sqlite.R
import com.db_sqlite.SQLiteDatabase.DatabaseHelper
import com.db_sqlite.UserInterface.ActivityMain.Companion.cvBack
import com.db_sqlite.UserInterface.ActivityMain.Companion.tvTitle

class FragmentMain : Fragment() {

    private lateinit var add: CardView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private lateinit var models: ArrayList<Model>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        cvBack.visibility = View.GONE
        tvTitle.setText(R.string.app_name)
        add = requireView().findViewById(R.id.add)
        add.setOnClickListener { (context as ActivityMain).showFragment(FragmentAdd()) }

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val databaseHelper = DatabaseHelper(context)

        models = ArrayList(databaseHelper.notes)

        recyclerView = requireView().findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = Adapter(requireContext(), models)
        recyclerView.adapter = adapter
    }
}