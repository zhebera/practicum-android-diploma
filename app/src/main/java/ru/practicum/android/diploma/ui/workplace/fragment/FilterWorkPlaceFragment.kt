package ru.practicum.android.diploma.ui.workplace.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceOfWorkBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.COUNTRY_BACKSTACK_KEY

class FilterWorkPlaceFragment : Fragment() {

    private var _binding: FragmentFilterPlaceOfWorkBinding? = null
    private val binding: FragmentFilterPlaceOfWorkBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterPlaceOfWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(findNavController().currentBackStackEntry?.savedStateHandle) {
            this?.getLiveData<Country>(COUNTRY_BACKSTACK_KEY)?.observe(viewLifecycleOwner) { country ->
                if (country != null) {
                    binding.etCountry.setText(country.name)
                    binding.tvSelect.visibility = View.VISIBLE
                }
            }
        }

        binding.etRegion.setOnClickListener {
            findNavController().navigate(R.id.action_filterWorkPlaceFragment_to_regionsWorkPlaceFragment)
        }

        binding.etCountry.setOnClickListener {
            findNavController().navigate(R.id.action_filterWorkPlaceFragment_to_countriesWorkPlaceFragment)
        }

        binding.ivFilterPlaceOfWorkBackButton.setOnClickListener {
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
