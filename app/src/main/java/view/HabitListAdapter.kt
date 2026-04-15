package view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.habittracker.R
import com.ubaya.habittracker.databinding.ItemHabitBinding
import model.Habit

class HabitListAdapter(
    private val habitList: ArrayList<Habit>,
    private val onIncrement: (Int) -> Unit,
    private val onDecrement: (Int) -> Unit
) : RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {

    // Icon map: key → emoji
    private val iconMap = mapOf(
        "Water"      to "💧",
        "Exercise"   to "💪",
        "Reading"    to "📚",
        "Meditation" to "🧘",
        "Sleep"      to "😴",
        "Diet"       to "🥗",
        "Study"      to "📖",
        "Walking"    to "🚶"
    )

    class HabitViewHolder(val binding: ItemHabitBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HabitViewHolder(binding)
    }

    override fun getItemCount() = habitList.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]
        with(holder.binding) {
            txtIcon.text = iconMap[habit.iconKey] ?: "⭐"
            txtHabitName.text = habit.name
            txtDescription.text = habit.description
            txtProgressCount.text = "${habit.progress} / ${habit.goal} ${habit.unit}"

            val percent = if (habit.goal > 0) (habit.progress * 100 / habit.goal) else 0
            progressBar.progress = percent

            val isCompleted = habit.progress >= habit.goal
            if (isCompleted) {
                txtStatus.text = "Completed ✓"
                txtStatus.setBackgroundResource(R.drawable.badge_completed)
                btnIncrement.isEnabled = false
                btnIncrement.alpha = 0.4f
            } else {
                txtStatus.text = "In Progress"
                txtStatus.setBackgroundResource(R.drawable.badge_in_progress)
                btnIncrement.isEnabled = true
                btnIncrement.alpha = 1f
            }

            btnIncrement.setOnClickListener { onIncrement(habit.id) }
            btnDecrement.setOnClickListener { onDecrement(habit.id) }
        }
    }

    fun updateList(newList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newList)
        notifyDataSetChanged()
    }
}