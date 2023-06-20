package com.db_sqlite.UserInterface

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.db_sqlite.R
import com.db_sqlite.SQLiteDatabase.DatabaseHelper

class Adapter(var context: Context, private var model: ArrayList<Model>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: ViewHolder, number: Int) {

        val p = model[number].position

        val databaseHelper = DatabaseHelper(context)
        val db = databaseHelper.writableDatabase
        @SuppressLint("Recycle") val c = db.rawQuery("SELECT * FROM test WHERE position = '$p'", null)
        c.moveToNext()
        holder.position.text = c.getString(c.getColumnIndex("position"))
        holder.id.text = c.getString(c.getColumnIndex("id"))
        holder.name.text = c.getString(c.getColumnIndex("name"))

        holder.cardview.setOnClickListener {
            (context as ActivityMain).showFragment(FragmentSecond(p))
        }

        holder.delete.setOnClickListener {
            databaseHelper.delete(p)
            model.removeAt(number)
            notifyItemRemoved(number)
            notifyItemRangeChanged(number, model.size);
        }
        db.close()
    }

    override fun getItemCount(): Int {
        return model.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var position: TextView
        var id: TextView
        var name: TextView
        var cardview: CardView
        var delete: CardView

        init {
            position = itemView.findViewById(R.id.position)
            id = itemView.findViewById(R.id.id)
            name = itemView.findViewById(R.id.name)
            cardview = itemView.findViewById(R.id.cardview)
            delete = itemView.findViewById(R.id.delete)
        }
    }
}

