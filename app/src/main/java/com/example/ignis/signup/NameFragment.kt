package com.example.ignis.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                val data = input.text.toString()
                listener.onButtonNameClicked(data)
            }
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