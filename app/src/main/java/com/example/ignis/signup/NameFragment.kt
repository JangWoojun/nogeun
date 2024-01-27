package com.example.ignis.signup

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.ignis.R
import com.example.ignis.databinding.FragmentNameBinding

class NameFragment : Fragment() {
    private var _binding: FragmentNameBinding? = null

    private val binding get() = _binding!!

    interface NameFragmentListener {
        fun onButtonNameClicked(data: String)
    }

    private lateinit var listener: NameFragmentListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            button.setOnClickListener {
                if (input.text.isNotEmpty()) {
                    val data = input.text.toString()
                    listener.onButtonNameClicked(data)
                }
            }

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
        }

    }

    fun setListener(listener: NameFragmentListener) {
        this.listener = listener
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}