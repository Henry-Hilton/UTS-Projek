package model

data class Habit(
    var id: Int,
    var name: String,
    var description: String,
    var goal: Int,
    var unit: String,
    var progress: Int = 0,
    var iconKey: String
)