package ru.practicum.android.diploma.ui.filter.viewmodel


sealed interface FilterState {
    data class Filter(val salary: String?,
                          val onlyWithSalary: Boolean?,
                          val industryName: String?,
                          val countryName: String?,
                          val regionName: String?) : FilterState

    object SetSettings : FilterState
    object ClearFilter: FilterState
}
