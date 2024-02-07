package ru.practicum.android.diploma.ui.filter.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
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
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackStackListeners()
        setButtonsListeners()
        setTextChangedListeners()
    }

    private fun setTextChangedListeners() {
        val industryTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                with(binding.industry) {
                    if (s.isNullOrBlank()) {
                        setEndIconDrawable(R.drawable.arrow_forward)
                        setEndIconOnClickListener {
                            findNavController().navigate(R.id.action_filterFragment_to_filterIndustryFragment)
                        }
                    } else {
                        endIconMode = TextInputLayout.END_ICON_CUSTOM
                        setEndIconDrawable(R.drawable.close)

                        setEndIconOnClickListener {
                            s.clear()
                            industry = null
                            findNavController().currentBackStackEntry?.savedStateHandle?.set(INDUSTRY_BACKSTACK_KEY, industry)
                        }
                    }
                }
            }
        }

        val workPlaceTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                with(binding.placeOfWork) {
                    if (s.isNullOrBlank()) {
                        setEndIconDrawable(R.drawable.arrow_forward)
                        setEndIconOnClickListener {
                            findNavController().navigate(R.id.action_filterFragment_to_filterWorkPlaceFragment)
                        }
                    } else {
                        endIconMode = TextInputLayout.END_ICON_CUSTOM
                        setEndIconDrawable(R.drawable.close)

                        setEndIconOnClickListener {
                            s.clear()
                            country = null
                            region = null
                            findNavController().currentBackStackEntry?.savedStateHandle?.set(COUNTRY_BACKSTACK_KEY, country)
                            findNavController().currentBackStackEntry?.savedStateHandle?.set(REGION_BACKSTACK_KEY, region)
                        }
                    }
                }
            }
        }

        binding.etIndustry.addTextChangedListener(industryTextWatcher)
        binding.etPlaceOfWork.addTextChangedListener(workPlaceTextWatcher)
    }



    private fun setBackStackListeners() {
        with(findNavController().currentBackStackEntry?.savedStateHandle) {
            this?.getLiveData<Industry>(INDUSTRY_BACKSTACK_KEY)?.observe(
                viewLifecycleOwner
            ) { backStackIndustry ->
                if (backStackIndustry != null) {
                    industry = backStackIndustry

                    binding.etIndustry.setText(industry?.name)
                    binding.tvApply.isVisible = true
                    binding.tvRemove.isVisible = true
                }
            }

            this?.getLiveData<Country>(COUNTRY_BACKSTACK_KEY)?.observe(
                viewLifecycleOwner
            ) { backStackCountry ->
                if (backStackCountry != null) {
                    country = backStackCountry

                    binding.etIndustry.setText(country?.name)
                    binding.tvApply.isVisible = true
                    binding.tvRemove.isVisible = true
                }
            }

            this?.getLiveData<Region>(REGION_BACKSTACK_KEY)?.observe(
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
    }

    private fun setButtonsListeners() {
        binding.placeOfWork.setEndIconOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_filterWorkPlaceFragment)
        }

        binding.industry.setEndIconOnClickListener {
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
