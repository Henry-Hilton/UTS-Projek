package view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.habittracker.R
import com.ubaya.habittracker.databinding.ItemHabitBinding
import model.Habit

// Interface — NMP Week 1 (HobbyListener pattern)
interface HabitActionListener {
    fun onIncrement(habitId: Int)
    fun onDecrement(habitId: Int)
}

class HabitListAdapter(
    private val habitList: ArrayList<Habit>,
    private val listener: HabitActionListener
) : RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {

    // NMP Week 1 — when expression instead of mapOf
    private fun getIcon(iconKey: String): String {
        return when (iconKey) {
            "Water"      -> "💧"
            "Exercise"   -> "💪"
            "Reading"    -> "📚"
            "Meditation" -> "🧘"
            "Sleep"      -> "😴"
            "Diet"       -> "🥗"
            "Study"      -> "📖"
            "Walking"    -> "🚶"
            else         -> "⭐"
        }
    }

    // NMP Week 4 — ViewHolder inner class
    class HabitViewHolder(val binding: ItemHabitBinding)
        : RecyclerView.ViewHolder(binding.root)

    // NMP Week 4 — onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HabitViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    // NMP Week 4 — onBindViewHolder
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]

        holder.binding.txtIcon.text = getIcon(habit.iconKey)
        holder.binding.txtHabitName.text = habit.name
        holder.binding.txtDescription.text = habit.description
        holder.binding.txtProgressCount.text =
            "${habit.progress} / ${habit.goal} ${habit.unit}"

        val percent = if (habit.goal > 0) (habit.progress * 100 / habit.goal) else 0
        holder.binding.progressBar.progress = percent

        val isCompleted = habit.progress >= habit.goal
        if (isCompleted) {
            holder.binding.txtStatus.text = "Completed"
            holder.binding.txtStatus.setBackgroundResource(R.drawable.badge_completed)
            holder.binding.btnIncrement.isEnabled = false
        } else {
            holder.binding.txtStatus.text = "In Progress"
            holder.binding.txtStatus.setBackgroundResource(R.drawable.badge_in_progress)
            holder.binding.btnIncrement.isEnabled = true
        }

        // NMP Week 1 — interface callbacks
        holder.binding.btnIncrement.setOnClickListener {
            listener.onIncrement(habit.id)
        }
        holder.binding.btnDecrement.setOnClickListener {
            listener.onDecrement(habit.id)
        }
    }

    // NMP Week 6 — notifyDataSetChanged
    fun updateList(newList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newList)
        notifyDataSetChanged()
    }
}