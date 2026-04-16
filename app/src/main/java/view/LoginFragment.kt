package view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ubaya.habittracker.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    // NMP Week 3 - companion object for constants
    // avoids hardcoded strings scattered around the class
    companion object {
        const val VALID_USERNAME = "student"
        const val VALID_PASSWORD = "123"
    }

    // ANMP Week 1 - ViewBinding in Fragment (onCreateView pattern)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // NMP Week 2 - setOnClickListener
        binding.btnLogin.setOnClickListener {

            // NMP Week 6 - EditText .text.toString()
            val username = binding.txtUsername.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()

            if (username == VALID_USERNAME && password == VALID_PASSWORD) {
                // ANMP Week 1 - SafeArgs navigation
                // ANMP Week 2 - popUpTo + popUpToInclusive defined in nav_graph
                // ensures login is removed from backstack after navigating
                val action = LoginFragmentDirections.actionDashboard()
                it.findNavController().navigate(action)
            } else {
                // NMP Week 5 - View.VISIBLE / View.GONE
                binding.txtError.visibility = View.VISIBLE
            }
        }

        // NMP Week 2 - listener pattern
        // hides error message when user starts typing again
        binding.txtUsername.setOnFocusChangeListener { view, hasFocus ->
            binding.txtError.visibility = View.GONE
        }
        binding.txtPassword.setOnFocusChangeListener { view, hasFocus ->
            binding.txtError.visibility = View.GONE
        }
    }
}