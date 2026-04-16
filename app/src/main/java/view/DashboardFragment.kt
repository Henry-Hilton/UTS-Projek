package view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.habittracker.databinding.FragmentDashboardBinding
import viewmodel.HabitViewModel

class DashboardFragment : Fragment(), HabitActionListener {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: HabitViewModel
    private lateinit var habitAdapter: HabitListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)
        viewModel.loadHabits()

        // NMP Week 4 — adapter initialized here, after viewModel is ready
        habitAdapter = HabitListAdapter(ArrayList(), this)

        binding.recViewHabits.layoutManager = LinearLayoutManager(context)
        binding.recViewHabits.adapter = habitAdapter

        binding.fabAddHabit.setOnClickListener {
            val action = DashboardFragmentDirections.actionNewHabit()
            it.findNavController().navigate(action)
        }

        observeViewModel()
    }

    // ANMP Week 3 — LiveData Observer
    private fun observeViewModel() {
        viewModel.habitsLD.observe(viewLifecycleOwner, Observer { habits ->
            habitAdapter.updateList(habits)
            if (habits.isEmpty()) {
                binding.txtEmpty.visibility = View.VISIBLE
                binding.recViewHabits.visibility = View.GONE
            } else {
                binding.txtEmpty.visibility = View.GONE
                binding.recViewHabits.visibility = View.VISIBLE
            }
        })
    }

    // NMP Week 1 — HabitActionListener interface implementation
    override fun onIncrement(habitId: Int) {
        viewModel.incrementProgress(habitId)
    }

    override fun onDecrement(habitId: Int) {
        viewModel.decrementProgress(habitId)
    }
}