package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.response.*
import ru.practicum.android.diploma.domain.models.*

class VacanciesConverter {

    fun convert(response: VacanciesResponse): Vacancies {
        return with(response) {
            Vacancies(
                found = this.found,
                items = convertItems(this.items),
                page = this.page,
                pages = this.pages,
                per_page = this.per_page
            )
        }
    }

    private fun convertItems(items: List<VacancyResponse>): List<Vacancy> {
        return items.map { vacancy ->
            Vacancy(
                vacancy.id,
                vacancy.name,
                convertArea(vacancy.area),
                convertAddress(vacancy.address),
                convertEmployer(vacancy.employer),
                convertDepartment(vacancy.department),
                convertSalary(vacancy.salary)
            )
        }
    }

    private fun convertArea(area: AreaResponse): Area {
        return Area(
            id = area.id,
            name = area.name,
            url = area.url
        )
    }

    private fun convertAddress(address: AddressResponse?): Address? {
        return address?.let {
            Address(
                building = address.building,
                city = address.city,
                description = address.description,
                lat = address.lat,
                lng = address.lng,
                street = address.street
            )
        }
    }

    private fun convertEmployer(employer: EmployerResponse): Employer {
        return Employer(
            alternate_url = employer.alternate_url,
            blacklisted = employer.blacklisted,
            id = employer.id,
            logo_urls = convertLogoUrls(employer.logo_urls),
            name = employer.name,
            trusted = employer.trusted,
            url = employer.url
        )
    }

    private fun convertLogoUrls(logoUrls: LogoUrlsResponse?): LogoUrls? {
        return logoUrls?.let { LogoUrls(it.original) }
    }


    private fun convertDepartment(department: DepartmentResponse?): Department? {
        return department?.let {
            Department(
                id = it.id,
                name = department.name
            )
        }
    }

    private fun convertSalary(salary: SalaryResponse?): Salary? {
        return salary?.let {
            Salary(
                currency = salary.currency,
                from = salary.from,
                gross = salary.gross,
                to = salary.to
            )
        }
    }
}
