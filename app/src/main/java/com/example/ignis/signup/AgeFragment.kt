package com.example.ignis.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ignis.databinding.FragmentAgeBinding
import com.example.ignis.main.MainActivity

class AgeFragment : Fragment() {

    private var _binding: FragmentAgeBinding? = null

    private val binding get() = _binding!!

    companion object {
        private const val ARG_DATA = "data"

        fun newInstance(data: String): AgeFragment {
            val fragment = AgeFragment()
            val args = Bundle()
            args.putString(ARG_DATA, data)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            button.setOnClickListener {
                val data = arguments?.getString(ARG_DATA)
                val response = SignupRequest(input.text.toString().toInt(), data.toString())
                (activity as? SignUpActivity)?.onButtonAgeClicked(response)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}