package view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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

    // NMP Week 1 - array of options
    private val iconOptions = arrayOf(
        "Water", "Exercise", "Reading", "Meditation",
        "Sleep", "Diet", "Study", "Walking"
    )

    // tracks currently selected icon from spinner
    private var selectedIcon = "Water"

    // ANMP Week 1 - ViewBinding in Fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ANMP Week 3 - shared ViewModel across fragments
        viewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)

        // NMP Week 5 - ArrayAdapter + Spinner setup
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            iconOptions
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIcon.adapter = spinnerAdapter

        // NMP Week 5 - onItemSelectedListener with anonymous object
        binding.spinnerIcon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedIcon = iconOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        // NMP Week 2 - button click listener
        binding.btnCreateHabit.setOnClickListener {
            createHabit()
        }
    }

    private fun createHabit() {
        // NMP Week 6 - EditText .text.toString()
        val name = binding.txtHabitName.text.toString().trim()
        val desc = binding.txtDescription.text.toString().trim()
        val goalStr = binding.txtGoal.text.toString().trim()
        val unit = binding.txtUnit.text.toString().trim()

        // NMP Week 2 - Toast for user feedback
        if (name.isEmpty() || desc.isEmpty() || goalStr.isEmpty() || unit.isEmpty()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val goal = goalStr.toInt()

        if (goal <= 0) {
            Toast.makeText(context, "Goal must be a positive number", Toast.LENGTH_SHORT).show()
            return
        }

        // NMP Week 1 - positional constructor arguments
        val newHabit = Habit(0, name, desc, goal, unit, 0, selectedIcon)

        // ANMP Week 3 - ViewModel handles data
        viewModel.addHabit(newHabit)

        // NMP Week 2 - Toast feedback on success
        Toast.makeText(context, "Habit created!", Toast.LENGTH_SHORT).show()

        // ANMP Week 2 - popBackStack() navigates back
        findNavController().popBackStack()
    }
}