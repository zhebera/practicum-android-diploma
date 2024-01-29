package ru.practicum.android.diploma.ui.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentVacancyDescriptionBinding
import ru.practicum.android.diploma.domain.models.VacancyDescription
import ru.practicum.android.diploma.ui.search.viewmodel.VacancyDescriptionState
import ru.practicum.android.diploma.ui.search.viewmodel.VacancyDescriptionViewModel

class VacancyDescriptionFragment : Fragment() {

    private var _binding: FragmentVacancyDescriptionBinding? = null
    private val binding: FragmentVacancyDescriptionBinding get() = _binding!!

    private val viewModel: VacancyDescriptionViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVacancyDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getVacancyDescription("88114733")
        viewModel.vacancyDescriptionState.observe(viewLifecycleOwner) {
            renderState(it)
        }
        binding.textView
    }

    private fun renderState(vacancyDescriptionState: VacancyDescriptionState) {
        when (vacancyDescriptionState) {
            is VacancyDescriptionState.Loading -> showLoading()
            is VacancyDescriptionState.Error -> showError(vacancyDescriptionState.message)
            is VacancyDescriptionState.Content -> showContent(vacancyDescriptionState.data)
        }
    }

    private fun showLoading() {
        binding.textView.text = "Загрузка"
    }

    private fun showError(message: String) {
        binding.textView.text = message
    }

    private fun showContent(data: VacancyDescription) {
        binding.textView.text = data.employment?.name
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
