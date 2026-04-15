package view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ubaya.habittracker.databinding.FragmentNewHabitBinding
import model.Habit
import viewmodel.HabitViewModel

class NewHabitFragment : Fragment() {

    private lateinit var binding: FragmentNewHabitBinding
    private lateinit var viewModel: HabitViewModel

    // Icon options for Spinner (Week 2: Spinner / ArrayAdapter)
    private val iconOptions = arrayOf(
        "Water", "Exercise", "Reading", "Meditation",
        "Sleep", "Diet", "Study", "Walking"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Shared ViewModel with DashboardFragment using requireActivity() scope
        viewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)

        // Setup Spinner with ArrayAdapter (similar to Week 2 dropdown tutorial)
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            iconOptions
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIcon.adapter = spinnerAdapter

        binding.btnCreateHabit.setOnClickListener {
            val name = binding.txtHabitName.text.toString().trim()
            val desc = binding.txtDescription.text.toString().trim()
            val goalStr = binding.txtGoal.text.toString().trim()
            val unit = binding.txtUnit.text.toString().trim()
            val selectedIcon = iconOptions[binding.spinnerIcon.selectedItemPosition]

            // Basic validation
            if (name.isEmpty() || desc.isEmpty() || goalStr.isEmpty() || unit.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val goal = goalStr.toIntOrNull()
            if (goal == null || goal <= 0) {
                Toast.makeText(context, "Goal must be a positive number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newHabit = Habit(
                id = 0,  // will be assigned by ViewModel
                name = name,
                description = desc,
                goal = goal,
                unit = unit,
                progress = 0,
                iconKey = selectedIcon
            )

            viewModel.addHabit(newHabit)

            // Navigate back to Dashboard
            findNavController().popBackStack()
        }
    }
}