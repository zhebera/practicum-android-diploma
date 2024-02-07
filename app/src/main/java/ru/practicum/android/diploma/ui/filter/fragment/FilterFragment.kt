package ru.practicum.android.diploma.ui.filter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.util.COUNTRY_BACKSTACK_KEY
import ru.practicum.android.diploma.util.INDUSTRY_BACKSTACK_KEY
import ru.practicum.android.diploma.util.REGION_BACKSTACK_KEY

class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private var country: Country? = null
    private var region: Region? = null
    private var industry: Industry? = null

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

        setBackStackListeners()
        setButtonsListeners()
    }

    private fun setBackStackListeners() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Industry>(INDUSTRY_BACKSTACK_KEY)?.observe(
            viewLifecycleOwner
        ) { backStackIndustry ->
            if (backStackIndustry != null) {
                industry = backStackIndustry

                binding.etIndustry.setText(industry?.name)
                binding.tvApply.isVisible = true
                binding.tvRemove.isVisible = true
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Country>(COUNTRY_BACKSTACK_KEY)?.observe(
            viewLifecycleOwner
        ) { backStackCountry ->
            if (backStackCountry != null) {
                country = backStackCountry

                binding.etIndustry.setText(country?.name)
                binding.tvApply.isVisible = true
                binding.tvRemove.isVisible = true
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Region>(REGION_BACKSTACK_KEY)?.observe(
            viewLifecycleOwner
        ) { backStackRegion ->
            if (backStackRegion != null) {
                region = backStackRegion

                val fullWorkPlace = "${country?.name}, ${region?.name}"
                binding.etIndustry.setText(fullWorkPlace)
                binding.tvApply.isVisible = true
                binding.tvRemove.isVisible = true
            }
        }
    }

    private fun setButtonsListeners() {
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
