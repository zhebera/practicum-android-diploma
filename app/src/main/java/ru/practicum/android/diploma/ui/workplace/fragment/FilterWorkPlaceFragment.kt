package ru.practicum.android.diploma.ui.workplace.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceOfWorkBinding

class FilterWorkPlaceFragment : Fragment() {

    private var _binding: FragmentFilterPlaceOfWorkBinding? = null
    private val binding: FragmentFilterPlaceOfWorkBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterPlaceOfWorkBinding.inflate(inflater, container, false)
        return binding.root

        binding.etRegion.setOnClickListener {
            findNavController().navigate(R.id.action_filterWorkPlaceFragment_to_regionsWorkPlaceFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
