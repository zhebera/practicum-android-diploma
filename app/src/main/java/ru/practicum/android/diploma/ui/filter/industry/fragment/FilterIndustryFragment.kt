package ru.practicum.android.diploma.ui.filter.industry.fragment

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
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.filter.industry.adapter.IndustriesAdapter
import ru.practicum.android.diploma.ui.filter.industry.viewmodel.FilterIndustriesState
import ru.practicum.android.diploma.ui.filter.industry.viewmodel.FilterIndustryViewModel
import ru.practicum.android.diploma.util.INDUSTRY_BACKSTACK_KEY

class FilterIndustryFragment : Fragment() {

    private var _binding: FragmentFilterIndustryBinding? = null
    private val binding: FragmentFilterIndustryBinding
        get() = _binding!!

    private val viewModel by viewModel<FilterIndustryViewModel>()
    private var editText: CharSequence? = null
    private val adapter = IndustriesAdapter { industry ->
        setCheckedData(industry)
    }

    private fun setCheckedData(industry: Industry) {
        viewModel.changeChecks(industry)
        setFilteredIndustries(editText)
    }

    private var textWatcher: TextWatcher? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvIndustries.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIndustries.adapter = adapter

        with(viewModel) {
            industriesState.observe(viewLifecycleOwner, ::renderState)
            checkedIndustries.observe(viewLifecycleOwner, ::renderButton)
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvSetIndustry.setOnClickListener {
            if (binding.tvSetIndustry.isVisible) {
                val industry = viewModel.getIndustriesList()?.filter { it.isChecked }?.first()
                findNavController().previousBackStackEntry?.savedStateHandle
                    ?.set(INDUSTRY_BACKSTACK_KEY, industry)
                findNavController().popBackStack()
            }
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editText = s
                setFilteredIndustries(s)
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

    private fun renderButton(checks: List<Boolean>) {
        binding.tvSetIndustry.isVisible = checks.contains(true)
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
        setFilteredIndustries(editText)
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

    private fun setFilteredIndustries(s: CharSequence?) {
        if (!s.isNullOrEmpty()) {
            viewModel.getIndustriesList()
                ?.let { adapter.setData(it.filter { it.name?.lowercase()?.contains(s) ?: false }) }
        } else {
            viewModel.getIndustriesList()?.let { adapter.setData(it) }
        }
    }

    private fun closeKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.etSearchIndustry.windowToken, 0)
    }

    override fun onResume() {
        super.onResume()

        val chosenIndustry = arguments?.getParcelable<Industry?>(INDUSTRY_ARG)
        viewModel.getIndustries(chosenIndustry)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val INDUSTRY_ARG = "industry_arg"

        fun createArgs(industry: Industry?): Bundle {
            val bundle = bundleOf()
            industry?.let { bundle.putAll(bundleOf(INDUSTRY_ARG to industry)) }

            return bundle
        }
    }
}
