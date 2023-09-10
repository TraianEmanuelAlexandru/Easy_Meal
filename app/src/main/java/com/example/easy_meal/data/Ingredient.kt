package com.example.easy_meal.data

class Ingredient internal constructor(val name: String, thumbnail: String) {
    val thumbnail: String
    var isSelected: Boolean
        private set

    init {
        this.thumbnail = "https://spoonacular.com/cdn/ingredients_100x100/$thumbnail"
        isSelected = false
    }

    fun setSelected() {
        isSelected = !isSelected
    }
}