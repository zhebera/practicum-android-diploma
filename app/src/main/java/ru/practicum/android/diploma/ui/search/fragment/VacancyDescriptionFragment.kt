package ru.practicum.android.diploma.ui.search.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.domain.models.VacancyDescription
import ru.practicum.android.diploma.ui.search.adapter.details.PhoneAdapter
import ru.practicum.android.diploma.ui.search.viewmodel.VacancyDescriptionState
import ru.practicum.android.diploma.ui.search.viewmodel.VacancyDescriptionViewModel
import ru.practicum.android.diploma.util.glide
import ru.practicum.android.diploma.util.parseSalary

class VacancyDescriptionFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!

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

    private lateinit var openEmail: (mail: String) -> Unit
    private lateinit var shareLink: (link: String) -> Unit
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

        val vacancyId = requireArguments().getSerializable(ARGS_VACANCY) as String

        val viewModel by viewModel<VacancyDescriptionViewModel> { parametersOf(vacancyId) }

        viewModel.vacancyDescriptionState.observe(viewLifecycleOwner) {
            renderState(it)

            openEmail = { mail -> viewModel.openEmail(mail) }
            shareLink = { link -> viewModel.shareLink(link) }
        }

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

    private fun renderState(vacancyDescriptionState: VacancyDescriptionState) {
        when (vacancyDescriptionState) {
            is VacancyDescriptionState.Loading -> showLoading()
            is VacancyDescriptionState.Error -> showError(vacancyDescriptionState.message)
            is VacancyDescriptionState.Content -> showContent(vacancyDescriptionState.data)
        }
    }
    private fun showLoading() {
        progressBar?.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        progressBar?.visibility = View.GONE
        contentContainer?.visibility = View.GONE
        placeholderContainer?.visibility = View.VISIBLE

        placeholderText?.text = message
        placeholderImage?.setImageResource(R.drawable.placeholder_error_server)
    }

    private fun showContent(vacancyDescription: VacancyDescription) {
        progressBar?.visibility = View.GONE
        contentContainer?.visibility = View.VISIBLE

        val phones = vacancyDescription.contacts?.phones
        val email = vacancyDescription.contacts?.email
        var conditionsFullString = ""
        var keySkillsFullString = ""

        title?.text = vacancyDescription.name
        vacancyDescription.salary?.let { salary?.text = parseSalary(
            vacancyDescription.salary.from,
            vacancyDescription.salary.to,
            vacancyDescription.salary.currency,
            requireView()
        ) }
        vacancyDescription.employer?.logoUrls?.original?.let {
            glide(requireContext(), it, employerLogo!!, RoundedCorners(roundedCorners))
        }
        vacancyDescription.employer?.name?.let { employerName?.text = vacancyDescription.employer.name }

        if (vacancyDescription.address != null) {
            val city = vacancyDescription.address.city
            val street = vacancyDescription.address.street
            val building = vacancyDescription.address.building

            val fullAddress = "${city?.let {city}}" +
                "${street?.let {", $street"}}" +
                "${building?.let {", $building"}}"

            employerAddress?.text = fullAddress
        } else {
            employerAddress?.text = vacancyDescription.area.name
        }

        vacancyDescription.experience?.let { experience?.text = vacancyDescription.experience.name }
        vacancyDescription.schedule?.let {
            conditionsFullString += vacancyDescription.schedule.name
            conditions?.text = conditionsFullString
        }
        vacancyDescription.employment?.let {
            conditionsFullString += ", ${vacancyDescription.employment.name}"
            conditions?.text = conditionsFullString
        }
        description?.text = Html.fromHtml(vacancyDescription.description, Html.FROM_HTML_MODE_COMPACT)

        if (vacancyDescription.keySkills.isNotEmpty()) {
            keySkillsContainer?.visibility = View.VISIBLE

            vacancyDescription.keySkills.forEach { skill -> keySkillsFullString += " •  ${skill.name}\n" }
            keySkills?.text = keySkillsFullString
        }

        vacancyDescription.contacts?.let {
            contactContainer?.visibility = View.VISIBLE

            phones?.let {
                phoneAdapter?.setData(phones)
                rvContainer?.visibility = View.VISIBLE
            }

            email?.let {
                contactEmail?.text = email
                contactEmailContainer?.visibility = View.VISIBLE
                contactEmail?.setOnClickListener { openEmail(email) }
            }
        }

        binding.shareButton.setOnClickListener { shareLink(vacancyDescription.url) }
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
