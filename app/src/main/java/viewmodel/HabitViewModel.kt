package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import model.Habit

class HabitViewModel : ViewModel() {

    // ANMP Week 3 - MutableLiveData observed by DashboardFragment
    val habitsLD = MutableLiveData<ArrayList<Habit>>()

    // internal list stored in ViewModel (survives config changes)
    private val habitList = ArrayList<Habit>()
    private var nextId = 1

    fun loadHabits() {
        habitsLD.value = habitList
    }

    fun addHabit(habit: Habit) {
        habit.id = nextId
        nextId = nextId + 1
        habitList.add(habit)

        // ANMP Week 3 - notify observers by updating LiveData
        habitsLD.value = habitList
    }

    fun incrementProgress(habitId: Int) {
        // NMP Week 1 - for loop to find habit by id
        for (habit in habitList) {
            if (habit.id == habitId) {
                if (habit.progress < habit.goal) {
                    habit.progress = habit.progress + 1
                    habitsLD.value = habitList
                }
                break
            }
        }
    }

    fun decrementProgress(habitId: Int) {
        // NMP Week 1 - for loop to find habit by id
        for (habit in habitList) {
            if (habit.id == habitId) {
                if (habit.progress > 0) {
                    habit.progress = habit.progress - 1
                    habitsLD.value = habitList
                }
                break
            }
        }
    }
}