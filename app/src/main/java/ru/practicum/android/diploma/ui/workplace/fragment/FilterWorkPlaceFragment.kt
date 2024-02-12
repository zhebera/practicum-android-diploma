package ru.practicum.android.diploma.ui.workplace.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceOfWorkBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.ui.regions.fragment.RegionsWorkPlaceFragment
import ru.practicum.android.diploma.util.COUNTRY_BACKSTACK_KEY
import ru.practicum.android.diploma.util.REGION_BACKSTACK_KEY

class FilterWorkPlaceFragment : Fragment() {

    private var _binding: FragmentFilterPlaceOfWorkBinding? = null
    private val binding: FragmentFilterPlaceOfWorkBinding
        get() = _binding!!
    private var regionModel: Region? = null
    private var countryModel: Country? = null

    private var countryContainer: TextInputLayout? = null
    private var countryTextInput: TextInputEditText? = null
    private var regionTextInput: TextInputEditText? = null
    private var regionContainer: TextInputLayout? = null
    private var submitButton: TextView? = null
    private var backButton: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterPlaceOfWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()
        setOnClickListeners()
        setTextChangedListeners()
        setBackStackListeners()
    }

    private fun setViews() {
        countryContainer = binding.country
        countryTextInput = binding.etCountry
        regionTextInput = binding.etRegion
        regionContainer = binding.region
        submitButton = binding.tvSelect
        backButton = binding.ivFilterPlaceOfWorkBackButton
    }

    private fun setOnClickListeners() {
        countryContainer?.setEndIconOnClickListener {
            findNavController().navigate(
                R.id.action_filterWorkPlaceFragment_to_countriesWorkPlaceFragment
            )
        }

        regionContainer?.setEndIconOnClickListener {
            val countryId = if (countryModel != null) {
                countryModel!!.id
            } else {
                ""
            }
            findNavController().navigate(
                R.id.action_filterWorkPlaceFragment_to_regionsWorkPlaceFragment,
                RegionsWorkPlaceFragment.createArgs(countryId)
            )
        }

        submitButton?.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(COUNTRY_BACKSTACK_KEY, countryModel)
            findNavController().previousBackStackEntry?.savedStateHandle?.set(REGION_BACKSTACK_KEY, regionModel)
            findNavController().popBackStack()
        }

        backButton?.setOnClickListener {
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    private fun setTextChangedListeners() {
        val countryTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                with(countryContainer!!) {
                    if (s.isNullOrBlank()) {
                        setEndIconDrawable(R.drawable.arrow_forward)
                        setEndIconOnClickListener {
                            findNavController().navigate(
                                R.id.action_filterWorkPlaceFragment_to_countriesWorkPlaceFragment
                            )
                        }
                    } else {
                        endIconMode = TextInputLayout.END_ICON_CUSTOM
                        setEndIconDrawable(R.drawable.close)

                        setEndIconOnClickListener {
                            s.clear()
                            regionTextInput?.text?.clear()

                            countryModel = null
                            regionModel = null

                            findNavController().currentBackStackEntry?.savedStateHandle?.set(
                                COUNTRY_BACKSTACK_KEY,
                                null
                            )

                            findNavController().currentBackStackEntry?.savedStateHandle?.set(
                                REGION_BACKSTACK_KEY,
                                null
                            )
                        }
                    }
                }
            }
        }

        val regionTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                with(regionContainer!!) {
                    if (s.isNullOrBlank()) {
                        setEndIconDrawable(R.drawable.arrow_forward)
                        setEndIconOnClickListener {
                            val countryId = if (countryModel != null) {
                                countryModel!!.id
                            } else {
                                ""
                            }
                            findNavController().navigate(
                                R.id.action_filterWorkPlaceFragment_to_regionsWorkPlaceFragment,
                                RegionsWorkPlaceFragment.createArgs(countryId)
                            )
                        }
                    } else {
                        endIconMode = TextInputLayout.END_ICON_CUSTOM
                        setEndIconDrawable(R.drawable.close)

                        setEndIconOnClickListener {
                            s.clear()
                            regionModel = null

                            findNavController().currentBackStackEntry?.savedStateHandle?.set(
                                REGION_BACKSTACK_KEY,
                                null
                            )
                        }
                    }
                }
            }
        }

        countryTextInput?.addTextChangedListener(countryTextWatcher)
        regionTextInput?.addTextChangedListener(regionTextWatcher)
    }

    private fun setBackStackListeners() {
        with(findNavController().currentBackStackEntry?.savedStateHandle) {
            this?.getLiveData<Country>(COUNTRY_BACKSTACK_KEY)?.observe(viewLifecycleOwner) { country ->
                countryModel = country
                if (country != null) {
                    countryTextInput?.setText(country.name)
                    submitButton?.visibility = View.VISIBLE
                }
            }

            this?.getLiveData<Region>(REGION_BACKSTACK_KEY)?.observe(viewLifecycleOwner) { region ->
                regionModel = region
                if (region != null) {
                    countryModel = region.parentCountry
                    regionTextInput?.setText(region.name)
                    countryTextInput?.setText(region.parentCountry?.name)
                    submitButton?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
