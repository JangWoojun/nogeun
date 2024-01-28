package com.example.ignis.signup

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.ignis.R
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
            input.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                    // 텍스트 변경 전에 호출
                }

                override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                    if (input.text.isNotEmpty()) {
                        val newBackgroundColor = ContextCompat.getColor(requireContext(),
                            R.color.primary
                        )
                        button.backgroundTintList = ColorStateList.valueOf(newBackgroundColor)
                    } else {
                        val newBackgroundColor = ContextCompat.getColor(requireContext(),
                            R.color.gray500
                        )
                        button.backgroundTintList = ColorStateList.valueOf(newBackgroundColor)
                    }
                }

                override fun afterTextChanged(editable: Editable?) {
                }
            })

            button.setOnClickListener {
                if (input.text.isNotEmpty()) {
                    success.visibility = View.VISIBLE
                    input.visibility = View.GONE
                    Handler().postDelayed({
                        val data = arguments?.getString(ARG_DATA)
                        val response = SignupRequest(input.text.toString().toInt(), data.toString())
                        (activity as? SignUpActivity)?.onButtonAgeClicked(response)
                    }, 1000)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}