package ru.practicum.android.diploma.ui.favourite.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouriteBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDescription
import ru.practicum.android.diploma.ui.details.fragment.VacancyDescriptionFragment
import ru.practicum.android.diploma.ui.favourite.viewmodel.FavouriteState
import ru.practicum.android.diploma.ui.favourite.viewmodel.FavouriteViewModel
import ru.practicum.android.diploma.ui.search.adapter.VacancyAdapter

class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private val adapter = VacancyAdapter(
        clickListener = { showVacancyDescription(it) }
    )
    private val viewModel by viewModel<FavouriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFavourite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavourite.adapter = adapter

        viewModel.favouriteVacancies.observe(viewLifecycleOwner, ::renderState)
    }

    private fun renderState(state: FavouriteState) {
        when (state) {
            is FavouriteState.Content -> showFavourites(state.data)
            is FavouriteState.Empty -> showEmpty()
        }
    }

    private fun showFavourites(listFavourites: List<VacancyDescription>) {
        binding.apply {
            llPlaceholder.isVisible = false
            rvFavourite.isVisible = true
        }

        adapter.setData(
            listFavourites.map {
                Vacancy(
                    it.id,
                    it.name,
                    it.area,
                    it.employer,
                    it.employment,
                    it.salary
                )
            }
        )
    }

    private fun showVacancyDescription(vacancy: Vacancy) {
        findNavController().navigate(
            R.id.action_favouriteFragment_to_vacancyDescriptionFragment,
            VacancyDescriptionFragment.createArgs(vacancy.id)
        )
    }

    private fun showEmpty() {
        binding.apply {
            llPlaceholder.isVisible = true
            rvFavourite.isVisible = false
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getVacancies()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
