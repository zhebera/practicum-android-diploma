package ru.practicum.android.diploma.ui.filter.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.ui.filter.viewmodel.FilterViewModel
import ru.practicum.android.diploma.util.COUNTRY_BACKSTACK_KEY
import ru.practicum.android.diploma.util.FILTER_KEY_APLLIED
import ru.practicum.android.diploma.util.INDUSTRY_BACKSTACK_KEY
import ru.practicum.android.diploma.util.REGION_BACKSTACK_KEY

class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private var country: Country? = null
    private var region: Region? = null
    private var industry: Industry? = null
    private val viewModel by viewModel<FilterViewModel>()

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

        setButtonsListeners()
        setTextChangedListeners()
        loadSharedPrefsFilter()
        setBackStackListeners()
    }

    private fun loadSharedPrefsFilter() {
        val filterModel = viewModel.getFilter()
        var placeOfWork = ""

        if (filterModel != null) {
            filterModel.country?.let {
                placeOfWork += it.name
                country = it
            }

            filterModel.region?.let {
                placeOfWork += ", ${it.name}"
                region = it
            }

            binding.etPlaceOfWork.setText(placeOfWork)

            filterModel.industry?.let {
                binding.etIndustry.setText(it.name)
                industry = it
            }

            filterModel.salary?.let { binding.textInputEditText.setText(it) }
            filterModel.onlyWithSalary?.let { binding.cbFilter.isChecked = it }
        }
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
                            findNavController().currentBackStackEntry?.savedStateHandle?.set(
                                INDUSTRY_BACKSTACK_KEY,
                                industry
                            )

                            setVisibilityApplyButton()
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
                            findNavController().currentBackStackEntry?.savedStateHandle?.set(
                                COUNTRY_BACKSTACK_KEY,
                                null
                            )
                            findNavController().currentBackStackEntry?.savedStateHandle?.set(
                                REGION_BACKSTACK_KEY,
                                null
                            )

                            setVisibilityApplyButton()
                        }
                    }
                }
            }
        }

        binding.etIndustry.addTextChangedListener(industryTextWatcher)
        binding.etPlaceOfWork.addTextChangedListener(workPlaceTextWatcher)

        binding.textInputEditText.doAfterTextChanged { setVisibilityApplyButton() }
    }

    private fun setBackStackListeners() {
        with(findNavController().currentBackStackEntry?.savedStateHandle) {
            this?.getLiveData<Industry>(INDUSTRY_BACKSTACK_KEY)?.observe(
                viewLifecycleOwner
            ) { backStackIndustry ->
                if (backStackIndustry != null) {
                    industry = backStackIndustry

                    binding.etIndustry.setText(industry?.name)
                    setVisibilityApplyButton()
                }
            }

            this?.getLiveData<Country>(COUNTRY_BACKSTACK_KEY)?.observe(
                viewLifecycleOwner
            ) { backStackCountry ->
                if (backStackCountry != null) {
                    country = backStackCountry

                    binding.etPlaceOfWork.setText(country?.name)
                    setVisibilityApplyButton()
                }
            }

            this?.getLiveData<Region>(REGION_BACKSTACK_KEY)?.observe(
                viewLifecycleOwner
            ) { backStackRegion ->
                if (backStackRegion != null) {
                    region = backStackRegion

                    val fullWorkPlace = "${country?.name}, ${region?.name}"
                    binding.etPlaceOfWork.setText(fullWorkPlace)
                    setVisibilityApplyButton()
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
            setVisibilityApplyButton()
        }

        binding.textInputLayout.setOnClickListener {
            setVisibilityApplyButton()
        }

        binding.ivFilterBackButton.setOnClickListener {
            returnToSearchFragment()
        }


        binding.tvRemove.setOnClickListener {
            viewModel.clearFilter()

            country = null
            region = null
            industry = null

            binding.etIndustry.setText("")
            binding.etPlaceOfWork.setText("")
            binding.textInputEditText.setText("")
            binding.cbFilter.isChecked = false
        }

        binding.tvApply.setOnClickListener {
            viewModel.saveFilter(
                country,
                region,
                industry,
                binding.textInputEditText.text.toString(),
                binding.cbFilter.isChecked
            )

            returnToSearchFragment()
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returnToSearchFragment()
            }
        })
    }

    private fun returnToSearchFragment() {
        with(findNavController()) {
            previousBackStackEntry?.savedStateHandle?.set(FILTER_KEY_APLLIED, checkFilter())
            popBackStack()
        }
    }

    private fun setVisibilityApplyButton() {
        binding.tvApply.visibility = View.VISIBLE
        binding.tvRemove.visibility = View.VISIBLE
    }

    private fun checkFilter(): Boolean {
        return (!binding.etPlaceOfWork.text.isNullOrEmpty()
            || !binding.etIndustry.text.isNullOrEmpty()
            || !binding.textInputEditText.text.isNullOrEmpty()
            || binding.cbFilter.isChecked)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
