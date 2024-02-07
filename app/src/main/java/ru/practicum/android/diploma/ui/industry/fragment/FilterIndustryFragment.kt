package ru.practicum.android.diploma.ui.industry.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.industry.adapter.IndustriesAdapter
import ru.practicum.android.diploma.ui.industry.viewmodel.FilterIndustriesState
import ru.practicum.android.diploma.ui.industry.viewmodel.FilterIndustryViewModel

class FilterIndustryFragment : Fragment() {

    private var _binding: FragmentFilterIndustryBinding? = null
    private val binding: FragmentFilterIndustryBinding
        get() = _binding!!

    private val viewModel by viewModel<FilterIndustryViewModel>()
    private val adapter = IndustriesAdapter { industry ->
        setCheckedData(industry)
    }

    private fun setCheckedData(industry: Industry) {
        if (industry.isChecked) {
            adapter.setData(listOf(industry))
        } else {
            viewModel.getIndustriesList()?.let { adapter.setData(it) }
        }
    }

    private var textWatcher: TextWatcher? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFilterIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvIndustries.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIndustries.adapter = adapter

        viewModel.industriesState.observe(viewLifecycleOwner, ::renderState)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    viewModel.getIndustriesList()
                        ?.let { adapter.setData(it.filter { it.name?.lowercase()?.contains(s) ?: false }) }
                } else {
                    viewModel.getIndustriesList()?.let { adapter.setData(it) }
                }

            }

            override fun afterTextChanged(s: Editable?) {
                with(binding.searchTextInputLayout) {
                    if (s.isNullOrBlank()) {
                        endIconMode = TextInputLayout.END_ICON_CUSTOM
                        setEndIconDrawable(R.drawable.search)
                        findViewById<View>(com.google.android.material.R.id.text_input_end_icon).isClickable = false
                    } else {
                        setEndIconDrawable(R.drawable.close)
                        setEndIconOnClickListener { s.clear() }
                    }
                }
            }
        }
        binding.etSearchIndustry.addTextChangedListener(textWatcher)

        binding.etSearchIndustry.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && !binding.etSearchIndustry.text.toString().isNullOrEmpty()) {
                closeKeyboard()
                binding.etSearchIndustry.clearFocus()
                true
            } else {
                false
            }
        }
    }

    private fun renderState(state: FilterIndustriesState) {
        when (state) {
            is FilterIndustriesState.Loading -> showLoading()
            is FilterIndustriesState.Error -> showError()
            is FilterIndustriesState.Content -> showContent(state.data)
        }
    }

    private fun showContent(data: List<Industry>) {
        binding.rvIndustries.isVisible = true
        binding.llPlaceholderIndustry.isVisible = false
        binding.tvSetIndustry.isVisible = false
        binding.pbLoading.isVisible = false

        adapter.setData(data)
    }

    private fun showError() {
        binding.rvIndustries.isVisible = false
        binding.llPlaceholderIndustry.isVisible = true
        binding.tvSetIndustry.isVisible = false
        binding.pbLoading.isVisible = false

        binding.ivPlaceholderImage.setImageDrawable(requireContext().getDrawable(R.drawable.placeholder_empty_list))
        binding.tvPlaceholderMessage.text = getString(R.string.industries_placeholder_message)
    }

    private fun showLoading() {
        binding.rvIndustries.isVisible = false
        binding.llPlaceholderIndustry.isVisible = false
        binding.tvSetIndustry.isVisible = false
        binding.pbLoading.isVisible = true
    }

    private fun closeKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.etSearchIndustry.windowToken, 0)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getIndustries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
