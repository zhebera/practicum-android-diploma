package ru.practicum.android.diploma.ui.search.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.domain.models.VacancyDescription
import ru.practicum.android.diploma.ui.search.detailsAdapter.PhoneAdapter
import ru.practicum.android.diploma.ui.search.viewmodel.VacancyDescriptionState
import ru.practicum.android.diploma.ui.search.viewmodel.VacancyDescriptionViewModel

class VacancyDescriptionFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!

    private val viewModel: VacancyDescriptionViewModel by viewModel()

    private var recyclerView: RecyclerView? = null
    private var phoneAdapter = PhoneAdapter(
        clickListener = {
            viewModel.doCall(it)
        }
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getVacancyDescription("92431480")
        viewModel.vacancyDescriptionState.observe(viewLifecycleOwner) {
            renderState(it)
        }

        recyclerView = binding.rwContacts
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = phoneAdapter
    }

    private fun renderState(vacancyDescriptionState: VacancyDescriptionState) {
        when (vacancyDescriptionState) {
            is VacancyDescriptionState.Loading -> showLoading()
            is VacancyDescriptionState.Error -> showError(vacancyDescriptionState.message)
            is VacancyDescriptionState.Content -> showContent(vacancyDescriptionState.data)
        }
    }
    private fun showLoading() {
        // TODO()
    }

    private fun showError(message: String) {
        // TODO()
    }

    private fun showContent(vacancyDescription: VacancyDescription) {

        val contacts = vacancyDescription.contacts
        val phones = vacancyDescription.contacts?.phones
        val email = vacancyDescription.contacts?.email

        contacts?.let {
            binding.contactsContainer.visibility = View.VISIBLE

            phones?.let {
                phoneAdapter.setData(phones)
                recyclerView?.visibility = View.VISIBLE
            }

            email?.let {
                binding.contactEmail.text = email
                binding.contactEmailContainer.visibility = View.VISIBLE
                binding.contactEmail.setOnClickListener {
                    viewModel.openEmail(email)
                }
            }
        }

        binding.shareButton.setOnClickListener{
            vacancyDescription.url.let { viewModel.shareLink(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
