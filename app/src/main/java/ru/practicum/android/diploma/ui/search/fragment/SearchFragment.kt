package ru.practicum.android.diploma.ui.search.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.details.fragment.VacancyDescriptionFragment
import ru.practicum.android.diploma.ui.search.adapter.VacancyAdapter
import ru.practicum.android.diploma.ui.search.viewmodel.SearchState
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.util.FILTER_KEY_APLLIED
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
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<Boolean>(FILTER_KEY_APLLIED)?.observe(viewLifecycleOwner) { filterStatus ->
                if (!binding.etSearch.text.isNullOrEmpty() && filterStatus) {
                    viewModel.searchDebounce(changedText = binding.etSearch.text.toString(), newFilter = true)
                }
            }

        viewModel.getFilterState()

        recyclerView = binding.rwResult
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = vacancyAdapter

        with(viewModel) {
            searchState.observe(viewLifecycleOwner, ::render)
            filterState.observe(viewLifecycleOwner, ::renderFilter)
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
                        newFilter = false
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
                viewModel.searchDebounce(binding.etSearch.text.toString(), false)
                closeKeyboard()
                binding.etSearch.clearFocus()
                true
            } else {
                false
            }
        }

        binding.rwResult.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val position = (binding.rwResult.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = vacancyAdapter.itemCount
                    if (position >= itemsCount - 1) {
                        if (!viewModel.checkLastPage()) {
                            viewModel.getNextPageData()
                            binding.pbProgressBar.isVisible = true
                        }
                    }
                }
            }
        })
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
                showContent(state.data.items, state.currentPage)
            }

            is SearchState.Loading -> {
                showLoading()
            }

            is SearchState.Error -> {
                showError(state.currentPage)
            }

            is SearchState.Empty -> {
                showEmpty()
            }
        }
    }

    private fun renderFilter(isFiltered: Boolean) {
        when (isFiltered) {
            false -> binding.ivFilter.setImageDrawable(requireContext().getDrawable(R.drawable.filter_off))
            true -> binding.ivFilter.setImageDrawable(requireContext().getDrawable(R.drawable.filter_on))
        }
    }

    private fun showContent(vacancies: List<Vacancy>, currentPage: Int?) {
        with(binding) {
            llContent.isVisible = true
            pbCentralProgressBar.isVisible = false
            llProblem.isVisible = false
            binding.pbProgressBar.isVisible = false

            if (currentPage != null && currentPage > 0) {
                vacancyAdapter.setNewPageData(vacancies)
            } else {
                vacancyAdapter.setData(vacancies)
            }

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
            binding.pbProgressBar.isVisible = false

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
            binding.pbProgressBar.isVisible = false

            ivPlaceholders.setImageResource(R.drawable.placeholder_before_search)
            closeKeyboard()
        }
    }

    private fun showError(currentPage: Int?) {
        with(binding) {
            if (currentPage != null) {
                llContent.isVisible = true
                pbCentralProgressBar.isVisible = false
                llProblem.isVisible = false
                binding.pbProgressBar.isVisible = false

                val toast = Toast.makeText(requireContext(), "Произошла ошибка", Toast.LENGTH_LONG)
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    val view = toast.view
                    view!!.background = requireContext().getDrawable(R.drawable.card_vacancy_background)
                    view.setBackgroundColor(requireContext().getColor(R.color.red))
                }
                toast.show()
            } else {
                llContent.isVisible = false
                pbCentralProgressBar.isVisible = false
                llProblem.isVisible = true
                tvPlaceholders.isVisible = true
                binding.pbProgressBar.isVisible = false

                ivPlaceholders.setImageResource(R.drawable.placeholder_no_internet)
                tvPlaceholders.text = getString(R.string.no_internet)
                closeKeyboard()
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            llContent.isVisible = false
            pbCentralProgressBar.isVisible = true
            llProblem.isVisible = false
            binding.pbProgressBar.isVisible = false
            closeKeyboard()
        }
    }

    private fun closeKeyboard() {
        val inputMethodManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }
}
