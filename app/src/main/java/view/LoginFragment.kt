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

    // Hardcoded credentials (as per project requirement)
    private val VALID_USERNAME = "student"
    private val VALID_PASSWORD = "123"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()

            if (username == VALID_USERNAME && password == VALID_PASSWORD) {
                // Navigate to Dashboard, remove Login from backstack (Week 2: popUpTo)
                val action = LoginFragmentDirections.actionDashboard()
                it.findNavController().navigate(action)
            } else {
                binding.txtError.visibility = View.VISIBLE
            }
        }

        // Hide error when user starts typing again
        binding.txtUsername.setOnFocusChangeListener { _, _ -> binding.txtError.visibility = View.GONE }
        binding.txtPassword.setOnFocusChangeListener { _, _ -> binding.txtError.visibility = View.GONE }
    }
}