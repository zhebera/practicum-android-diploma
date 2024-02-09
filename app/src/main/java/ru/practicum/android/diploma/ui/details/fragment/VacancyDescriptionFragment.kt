package ru.practicum.android.diploma.ui.details.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.domain.models.*
import ru.practicum.android.diploma.ui.details.adapter.PhoneAdapter
import ru.practicum.android.diploma.ui.details.viewmodel.VacancyDescriptionState
import ru.practicum.android.diploma.ui.details.viewmodel.VacancyDescriptionViewModel
import ru.practicum.android.diploma.util.loadImageIntoView
import ru.practicum.android.diploma.util.parseSalary

class VacancyDescriptionFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!
    private val viewModel by viewModel<VacancyDescriptionViewModel>()

    private var progressBar: ProgressBar? = null
    private var placeholderContainer: LinearLayout? = null
    private var placeholderImage: ImageView? = null
    private var placeholderText: TextView? = null
    private var contentContainer: ScrollView? = null
    private var title: TextView? = null
    private var salary: TextView? = null
    private var employerLogo: ImageView? = null
    private var employerName: TextView? = null
    private var employerAddress: TextView? = null
    private var experience: TextView? = null
    private var conditions: TextView? = null
    private var description: TextView? = null
    private var keySkills: TextView? = null
    private var keySkillsContainer: LinearLayout? = null
    private var contactContainer: LinearLayout? = null
    private var contactNameContainer: LinearLayout? = null
    private var contactEmailContainer: LinearLayout? = null
    private var rvContainer: RecyclerView? = null
    private var contactName: TextView? = null
    private var contactEmail: TextView? = null
    private var phoneAdapter: PhoneAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancyId = requireArguments().getSerializable(ARGS_VACANCY) as String

        viewModel.getVacancyDescription(vacancyId)

        with(viewModel) {
            vacancyDescriptionState.observe(viewLifecycleOwner, ::renderState)
            vacancyDescriptionDbState.observe(viewLifecycleOwner, ::renderBdState)
            isFavorite.observe(viewLifecycleOwner, ::renderFavorite)
        }

        binding.favouriteButton.setOnClickListener {
            viewModel.changeFavourite()
        }

        setViews()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkFavorite()
    }

    private fun renderState(vacancyDescriptionState: VacancyDescriptionState) {
        when (vacancyDescriptionState) {
            is VacancyDescriptionState.Loading -> showLoading()
            is VacancyDescriptionState.Error -> showError(vacancyDescriptionState.message)
            is VacancyDescriptionState.Content -> showContent(vacancyDescriptionState.data)
        }
    }

    private fun renderBdState(state: VacancyDescriptionState) {
        when (state) {
            is VacancyDescriptionState.Content -> showContent(state.data)
            else -> showError(getString(R.string.no_internet))
        }
    }

    private fun showLoading() {
        progressBar?.visibility = View.VISIBLE
    }

    private fun renderFavorite(favorite: Boolean) {
        if (favorite) {
            binding.favouriteButton.setImageDrawable(requireContext().getDrawable(R.drawable.favourites_on))
        } else {
            binding.favouriteButton.setImageDrawable(requireContext().getDrawable(R.drawable.favourites_off))
        }
    }

    private fun showError(message: String) {
        progressBar?.visibility = View.GONE
        contentContainer?.visibility = View.GONE
        placeholderContainer?.visibility = View.VISIBLE

        placeholderText?.text = message
        when (message) {
            getString(R.string.no_internet) -> placeholderImage?.setImageResource(R.drawable.placeholder_no_internet)
            getString(R.string.placeholder_details_error_message) ->
                placeholderImage?.setImageResource(R.drawable.placeholder_error_server)
        }
    }

    private fun showContent(vacancyDescription: VacancyDescription) {
        progressBar?.visibility = View.GONE
        placeholderContainer?.isVisible = false
        contentContainer?.visibility = View.VISIBLE

        title?.text = vacancyDescription.name

        vacancyDescription.salary?.let { setSalaryBlock(it) }

        vacancyDescription.employer?.let { setEmployerBlock(it) }

        setAddressBlock(vacancyDescription)

        vacancyDescription.experience?.let { setExperienceBlock(it) }

        setConditionsBlock(vacancyDescription)

        setDescriptionBlock(vacancyDescription.description)

        setKeySkillsBlock(vacancyDescription.keySkills)

        vacancyDescription.contacts?.let { setContactsBlock(it, viewModel) }

        binding.shareButton.setOnClickListener { viewModel.shareLink(vacancyDescription.url) }

        viewModel.checkFavorite()
    }

    private fun setSalaryBlock(salaryResponse: Salary) {
        salary?.text = parseSalary(
            salaryResponse.from,
            salaryResponse.to,
            salaryResponse.currency,
            requireView()
        )
    }

    private fun setEmployerBlock(employer: Employer) {
        employer.logoUrls?.original?.let {
            loadImageIntoView(requireContext(), it, employerLogo!!, RoundedCorners(roundedCorners))
        }

        employer.name?.let {
            employerName?.text = employer.name
        }
    }

    private fun setConditionsBlock(vacancyDescription: VacancyDescription) {
        var conditionsFullString = ""

        vacancyDescription.schedule?.let {
            conditionsFullString += vacancyDescription.schedule.name
            conditions?.text = conditionsFullString
        }

        vacancyDescription.employment?.let {
            conditionsFullString += ", ${vacancyDescription.employment.name}"
            conditions?.text = conditionsFullString
        }
    }

    private fun setAddressBlock(vacancyDescription: VacancyDescription) {
        if (vacancyDescription.address != null) {
            val city = vacancyDescription.address.city
            val street = vacancyDescription.address.street
            val building = vacancyDescription.address.building

            val fullAddress = "${city?.let { city }}" +
                "${street?.let { ", $street" }}" +
                "${building?.let { ", $building" }}"

            employerAddress?.text = fullAddress
        } else {
            employerAddress?.text = vacancyDescription.area.name
        }
    }

    private fun setDescriptionBlock(vacancyDescription: String) {
        description?.text = Html.fromHtml(vacancyDescription, Html.FROM_HTML_MODE_COMPACT)
    }

    private fun setExperienceBlock(experienceResponse: Experience) {
        experience?.text = experienceResponse.name
    }

    private fun setKeySkillsBlock(keySkillsResponse: List<KeySkill>) {
        var keySkillsFullString = ""

        if (keySkillsResponse.isNotEmpty()) {
            keySkillsContainer?.visibility = View.VISIBLE

            keySkillsResponse.forEach { skill -> keySkillsFullString += " •  ${skill.name}\n" }
            keySkills?.text = keySkillsFullString
        }
    }

    private fun setContactsBlock(contacts: Contacts, viewModel: VacancyDescriptionViewModel) {
        contactContainer?.visibility = View.VISIBLE

        contacts.phones?.let {
            phoneAdapter?.setData(contacts.phones)
            rvContainer?.visibility = View.VISIBLE
        }

        contacts.email?.let {
            contactEmail?.text = contacts.email
            contactEmailContainer?.visibility = View.VISIBLE
            contactEmail?.setOnClickListener { viewModel.openEmail(contacts.email) }
        }
    }

    private fun setViews() {
        phoneAdapter = PhoneAdapter(
            clickListener = {
                viewModel.doCall(it)
            }
        )

        rvContainer = binding.rwContacts
        rvContainer?.layoutManager = LinearLayoutManager(requireContext())
        rvContainer?.adapter = phoneAdapter

        progressBar = binding.progressBar
        placeholderContainer = binding.placeholder
        placeholderImage = binding.placeholderImage
        placeholderText = binding.placeholderMessage
        contentContainer = binding.vacancyContainer
        title = binding.title
        salary = binding.salary
        employerLogo = binding.employerLogo
        employerName = binding.employerName
        employerAddress = binding.employerAddress
        experience = binding.experience
        conditions = binding.conditions
        description = binding.description
        keySkills = binding.keySkills
        keySkillsContainer = binding.keySkillsContainer
        contactContainer = binding.contactsContainer
        contactNameContainer = binding.contactNameContainer
        contactEmailContainer = binding.contactEmailContainer
        contactName = binding.contactName
        contactEmail = binding.contactEmail
    }

    private fun setListeners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val roundedCorners = 12
        const val ARGS_VACANCY = "vacancy"
        fun createArgs(vacancyId: String): Bundle = bundleOf(ARGS_VACANCY to vacancyId)
    }
}
