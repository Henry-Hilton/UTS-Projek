package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import model.Habit

class HabitViewModel : ViewModel() {

    // LiveData observed by DashboardFragment
    val habitsLD = MutableLiveData<ArrayList<Habit>>()

    // Internal list stored in ViewModel (survives config changes)
    private val habitList = ArrayList<Habit>()
    private var nextId = 1

    fun loadHabits() {
        habitsLD.value = habitList
    }

    fun addHabit(habit: Habit) {
        habit.id = nextId++
        habitList.add(habit)
        habitsLD.value = habitList   // notify observers
    }

    fun incrementProgress(habitId: Int) {
        val habit = habitList.find { it.id == habitId } ?: return
        if (habit.progress < habit.goal) {
            habit.progress++
            habitsLD.value = habitList
        }
    }

    fun decrementProgress(habitId: Int) {
        val habit = habitList.find { it.id == habitId } ?: return
        if (habit.progress > 0) {
            habit.progress--
            habitsLD.value = habitList
        }
    }
}