package ru.practicum.android.diploma.ui.search.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.details.fragment.VacancyDescriptionFragment
import ru.practicum.android.diploma.ui.search.adapter.VacancyAdapter
import ru.practicum.android.diploma.ui.search.viewmodel.SearchState
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.util.getNumberString

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var recyclerView: RecyclerView? = null
    private var vacancyAdapter = VacancyAdapter(
        clickListener = {
            if (isClickAllowed) {
                showVacancyDescription(it)
            }
        }
    )
    private var isClickAllowed = true
    private val viewModel by viewModel<SearchViewModel>()
    private var textWatcher: TextWatcher? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = getString(R.string.main)

        recyclerView = binding.rwResult
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = vacancyAdapter

        with(viewModel) {
            searchState.observe(viewLifecycleOwner, ::render)
        }

        binding.ivFilter.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (!s.isNullOrEmpty()) {
                    viewModel.searchDebounce(
                        changedText = s.toString(),
                    )
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
        binding.etSearch.addTextChangedListener(textWatcher)

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && !binding.etSearch.text.toString().isNullOrEmpty()) {
                viewModel.searchDebounce(binding.etSearch.text.toString())
                closeKeyboard()
                binding.etSearch.clearFocus()
                true
            } else {
                false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showVacancyDescription(vacancy: Vacancy) {
        findNavController().navigate(
            R.id.action_searchFragment_to_vacancyDescriptionFragment,
            VacancyDescriptionFragment.createArgs(vacancy.id)
        )
    }

    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Default -> showDefault()

            is SearchState.Content -> {
                showContent(state.data.items)
            }

            is SearchState.Loading -> {
                showLoading()
            }

            is SearchState.Error -> {
                showError()
            }

            is SearchState.Empty -> {
                showEmpty()
            }
        }
    }

    private fun showContent(vacancies: List<Vacancy>) {
        with(binding) {
            llContent.isVisible = true
            pbCentralProgressBar.isVisible = false
            llProblem.isVisible = false

            vacancyAdapter.setData(vacancies)
            closeKeyboard()
            tvVacancyNumber.apply {
                text = viewModel.getCountVacancies()?.getNumberString(requireContext())
                measure(0, 0)
            }
        }
    }

    private fun showEmpty() {
        with(binding) {
            llContent.isVisible = false
            pbCentralProgressBar.isVisible = false
            llProblem.isVisible = true
            tvPlaceholders.isVisible = true

            ivPlaceholders.setImageResource(R.drawable.placeholder_no_vacancy_and_region)
            tvPlaceholders.text = getString(R.string.no_vacancy)
        }
    }

    private fun showDefault() {
        with(binding) {
            llContent.isVisible = false
            pbCentralProgressBar.isVisible = false
            llProblem.isVisible = true
            tvPlaceholders.isVisible = false
            ivPlaceholders.setImageResource(R.drawable.placeholder_before_search)
            closeKeyboard()
        }
    }

    private fun showError() {
        with(binding) {
            llContent.isVisible = false
            pbCentralProgressBar.isVisible = false
            llProblem.isVisible = true
            tvPlaceholders.isVisible = true
            ivPlaceholders.setImageResource(R.drawable.placeholder_no_internet)
            tvPlaceholders.text = getString(R.string.no_internet)
            closeKeyboard()
        }
    }

    private fun showLoading() {
        with(binding) {
            llContent.isVisible = false
            pbCentralProgressBar.isVisible = true
            llProblem.isVisible = false
            closeKeyboard()
        }
    }

    private fun closeKeyboard() {
        val inputMethodManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }
}
