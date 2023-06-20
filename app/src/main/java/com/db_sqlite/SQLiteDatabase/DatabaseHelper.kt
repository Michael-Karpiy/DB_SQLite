package com.db_sqlite.SQLiteDatabase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.db_sqlite.UserInterface.Model

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table $TABLE_NAME (position INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, name TEXT, description TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(" DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    val notes: ArrayList<Model>
        get() {
            val arrayList = ArrayList<Model>()
            val select_query = "SELECT *FROM $TABLE_NAME"
            val db = this.writableDatabase
            @SuppressLint("Recycle") val cursor = db.rawQuery(select_query, null)
            if (cursor.moveToFirst()) {
                do {
                    val model = Model()
                    model.setposition(cursor.getString(0).toInt())
                    model.setid(cursor.getString(1))
                    model.setname(cursor.getString(2))
                    model.setdescription(cursor.getString(3))
                    arrayList.add(model)
                } while (cursor.moveToNext())
            }
            return arrayList
        }

    fun delete(position: Int?) {
        val sqLiteDatabase = this.writableDatabase
        sqLiteDatabase.delete(TABLE_NAME, "position=$position", null)
        sqLiteDatabase.close()
    }

    fun deleteAll() {
        val sqLiteDatabase = this.writableDatabase
        sqLiteDatabase.delete(TABLE_NAME, null, null)
        sqLiteDatabase.close()
    }

    fun updateData(position: Int?, id: String?, name: String?, description: String?){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("position", position)
        values.put("id", id)
        values.put("name", name)
        values.put("description", description)
        db.execSQL("update $TABLE_NAME SET id = '$id', name = '$name', description = '$description' WHERE position = '$position';")
    }

    fun addNotes(position: Int?, id: String?, name: String?, description: String?) {
        val sqLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put("position", position)
        values.put("id", id)
        values.put("name", name)
        values.put("description", description)
        sqLiteDatabase.insert(TABLE_NAME, null, values)
        sqLiteDatabase.close()
    }

    companion object {
        const val DATABASE_NAME = "SQLiteDatabase"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "test"
    }
}