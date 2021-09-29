package com.example.myapplication

class User(var name: String, var birthday: String) {

    override fun toString(): String {
        return "$name $birthday"
    }

    fun getName(): Any {
        return this.name
    }

    fun getBirthday(): Any {
        return this.birthday

    }

}