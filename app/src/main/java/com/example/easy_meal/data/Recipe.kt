package com.example.easy_meal.data

class Recipe(
    val id: String,
    val title: String,
    val thumbnail: String?,
    val amountOfDishes: Int,
    val readyInMins: Int
) {

    override fun toString(): String {
        return title
    }
}