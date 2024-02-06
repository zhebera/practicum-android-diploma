package ru.practicum.android.diploma.ui.filter.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesInteractor

class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private val sharedPreferencesInteractor by inject<SharedPreferencesInteractor>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferencesInteractor.setCountry("Россия", "113")

        val filter = sharedPreferencesInteractor.getFilter()
        Log.d("D", "${filter!!.countryName}")

        binding.tvFilterMainText.text = getString(R.string.setting_of_filter)

        binding.etPlaceOfWork.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_filterWorkPlaceFragment)
        }

        binding.etIndustry.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_filterIndustryFragment)
        }

        binding.cbFilter.setOnCheckedChangeListener { _, isChecked ->
        }

        binding.ivFilterBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
