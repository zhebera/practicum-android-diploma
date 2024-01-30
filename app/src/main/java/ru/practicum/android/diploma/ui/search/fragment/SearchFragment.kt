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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.search.adapter.VacancyAdapter
import ru.practicum.android.diploma.ui.search.viewmodel.SearchState
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.util.VACANCY
import ru.practicum.android.diploma.util.getNumberString

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var recyclerView: RecyclerView? = null
    private var vacancyAdapter = VacancyAdapter(
        clickListener = {
            if (isClickAllowed) {
                clicker(it)
            }
        }
    )
    private var isClickAllowed = true
    private val searchViewModel by viewModel<SearchViewModel>()
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

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.searchState.observe(viewLifecycleOwner) { state ->
                render(state)
            }
        }

        binding.ivSearchImage.setOnClickListener {
            clearAll()
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                installSearchCancelButton(s)

                if (!s.isNullOrEmpty()) {
                    searchViewModel.searchDebounce(
                        changedText = s.toString()
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
        }
        binding.etSearch.addTextChangedListener(textWatcher)

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && !binding.etSearch.text.toString().isNullOrEmpty()) {
                searchViewModel.searchDebounce(binding.etSearch.text.toString())
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

    private fun clicker(vacancy: Vacancy) {
        val bundle = Bundle()
        bundle.putParcelable(VACANCY, vacancy)
        findNavController().navigate(R.id.searchFragment_to_vacancyFragment, bundle)
    }

    private fun render(state: SearchState) {
        when (state) {
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
            llProblem.visibility = View.GONE
            tvVacancyNumber.visibility = View.VISIBLE
            pbCentralProgressBar.visibility = View.GONE
            vacancyAdapter.setData(vacancies)
            closeKeyboard()
            tvVacancyNumber.apply {
                text = vacancies.count().getNumberString(requireContext())
                measure(0, 0)
            }
        }
    }

    private fun showEmpty() {
        with(binding) {
            pbCentralProgressBar.visibility = View.GONE
            llProblem.visibility = View.VISIBLE
            ivPlaceholders.setImageResource(R.drawable.placeholder_before_search)
            tvPlaceholders.visibility = View.GONE
        }
    }

    // todo
    private fun showError() {
        with(binding) {
            pbCentralProgressBar.visibility = View.GONE
            llProblem.visibility = View.VISIBLE
            tvPlaceholders.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        with(binding) {
            llProblem.visibility = View.GONE
            pbCentralProgressBar.visibility = View.VISIBLE
        }
    }

    private fun clearAll() {
        binding.etSearch.clearFocus()
        binding.etSearch.text.clear()
        vacancyAdapter.clear()
        binding.tvVacancyNumber.visibility = View.GONE
        closeKeyboard()
    }

    private fun closeKeyboard() {
        val inputMethodManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    private fun installSearchCancelButton(s: CharSequence?) {
        if (s?.isNotEmpty() == true) {
            binding.ivSearchImage.setImageResource(R.drawable.close)
        } else {
            binding.ivSearchImage.setImageResource(R.drawable.search)
        }
    }

}
