package ru.practicum.android.diploma.data.converters.db.schedule

import ru.practicum.android.diploma.data.db.entity.ScheduleEntity
import ru.practicum.android.diploma.domain.models.Schedule

object ScheduleEntityConveter {

    fun map(scheduleEntity: ScheduleEntity?): Schedule? {
        return if (scheduleEntity != null) {
            Schedule(
                scheduleEntity.id,
                scheduleEntity.name
            )
        } else {
            null
        }
    }

    fun map(schedule: Schedule?): ScheduleEntity? {
        return if (schedule != null) {
            ScheduleEntity(
                schedule.id,
                schedule.name
            )
        } else {
            null
        }
    }
}
