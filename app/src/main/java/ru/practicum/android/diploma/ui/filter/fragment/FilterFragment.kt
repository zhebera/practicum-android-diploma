package ru.practicum.android.diploma.ui.filter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvFilterMainText.text = getString(R.string.setting_of_filter)

        var country = "Россия"
        var region = "Москва"
        var industry = "IT"

        var isFilled = false

        binding.etPlaceOfWork.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_filterWorkPlaceFragment)
        }

        binding.etIndustry.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_filterIndustryFragment)
        }

        binding.cbFilter.setOnCheckedChangeListener { _, isChecked ->
        }

        // Тестовая кнопка для проверки заполнения TextInput
        binding.testButton.setOnClickListener {
            if (!isFilled) {
                binding.testButton.text = "Очистить TextInput"
                applyTextAndHint(
                    layout = binding.placeOfWork,
                    editText = binding.etPlaceOfWork,
                    text = "$country, $region",
                    hint = getString(R.string.place_of_work)
                )

                applyTextAndHint(
                    layout = binding.industry,
                    editText = binding.etIndustry,
                    text = industry,
                    hint = getString(R.string.industry)
                )
                isFilled = true
            } else {
                binding.testButton.text = "Заполнить TextInput"
                applyTextAndHint(
                    layout = binding.placeOfWork,
                    editText = binding.etPlaceOfWork,
                    text = getString(R.string.place_of_work),
                    hint = null
                )

                applyTextAndHint(
                    layout = binding.industry,
                    editText = binding.etIndustry,
                    text = getString(R.string.industry),
                    hint = null
                )
                isFilled = false
            }
        }
    }

    // Функция для заполнения TextInput
    fun applyTextAndHint(layout: TextInputLayout, editText: TextInputEditText, text: String?, hint: String?) {
        layout.hint = hint
        editText.setText(text)
        if (!hint.isNullOrBlank()) {
            editText.setTextColor(resources.getColor(R.color.black_white))
            editText.setPadding(
                0,
                resources.getDimensionPixelSize(R.dimen.workplace_edittext_top_padding),
                0,
                0
            )
        } else {
            editText.setTextColor(resources.getColor(R.color.grey))
            editText.setPadding(0, 0, 0, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
