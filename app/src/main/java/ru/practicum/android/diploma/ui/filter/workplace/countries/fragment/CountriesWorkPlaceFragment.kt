package ru.practicum.android.diploma.ui.filter.workplace.countries.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterCountryBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.ui.filter.workplace.countries.adapter.CountriesAdapter
import ru.practicum.android.diploma.ui.filter.workplace.countries.viewmodel.CountriesState
import ru.practicum.android.diploma.ui.filter.workplace.countries.viewmodel.CountriesViewModel
import ru.practicum.android.diploma.util.COUNTRY_BACKSTACK_KEY

class CountriesWorkPlaceFragment : Fragment() {

    private var _binding: FragmentFilterCountryBinding? = null
    private val binding: FragmentFilterCountryBinding
        get() = _binding!!
    private val viewModel by viewModel<CountriesViewModel>()
    private val adapter = CountriesAdapter {
        selectCountry(it)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerFilterCountry.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFilterCountry.adapter = adapter

        viewModel.countriesState.observe(viewLifecycleOwner, ::renderState)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun selectCountry(country: Country) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(COUNTRY_BACKSTACK_KEY, country)
        findNavController().popBackStack()
    }

    private fun renderState(state: CountriesState) {
        when (state) {
            is CountriesState.Loading -> showLoading()
            is CountriesState.Error -> showError()
            is CountriesState.Content -> showContent(state.data)
        }
    }

    private fun showLoading() {
        binding.recyclerFilterCountry.isVisible = false
        binding.llProblem.isVisible = false
        binding.pbLoading.isVisible = true
    }

    private fun showError() {
        binding.recyclerFilterCountry.isVisible = false
        binding.llProblem.isVisible = true
        binding.pbLoading.isVisible = false

        binding.ivPlaceholders.setImageDrawable(requireContext().getDrawable(R.drawable.placeholder_get_list))
        binding.tvPlaceholders.text = requireContext().getText(R.string.cant_get_list)
    }

    private fun showContent(data: List<Country>) {
        binding.recyclerFilterCountry.isVisible = true
        binding.llProblem.isVisible = false
        binding.pbLoading.isVisible = false

        adapter.clear()
        adapter.setData(data)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCountries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
