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

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: HabitViewModel
    private val habitAdapter = HabitListAdapter(arrayListOf(),
        onIncrement = { id -> viewModel.incrementProgress(id) },
        onDecrement = { id -> viewModel.decrementProgress(id) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel (Week 3)
        viewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)
        viewModel.loadHabits()

        // Setup RecyclerView
        binding.recViewHabits.layoutManager = LinearLayoutManager(context)
        binding.recViewHabits.adapter = habitAdapter

        // FAB → Navigate to New Habit screen (Week 1: SafeArgs navigation)
        binding.fabAddHabit.setOnClickListener {
            val action = DashboardFragmentDirections.actionNewHabit()
            it.findNavController().navigate(action)
        }

        observeViewModel()
    }

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
}