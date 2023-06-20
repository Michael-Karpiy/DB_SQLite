package com.db_sqlite.UserInterface

class Model {

    @com.google.gson.annotations.SerializedName("position")
    var position: Int? = null

    @com.google.gson.annotations.SerializedName("id")
    var id: String? = null

    @com.google.gson.annotations.SerializedName("name")
    var name: String? = null

    @com.google.gson.annotations.SerializedName("description")
    var description: String? = null

    fun setposition(position: Int?) { this.position = position }
    fun setid(id: String?) { this.id = id }
    fun setname(name: String?) { this.name = name }
    fun setdescription(description: String?) { this.description = description }
}