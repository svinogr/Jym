package info.upump.mycompose.models.entity

import android.util.Log
import info.upump.database.entities.CycleEntity
import java.util.Date

class Cycle(
    var workoutList: List<Workout> = ArrayList(),
    var isDefaultType: Boolean = false,
  //  var image: String? = "",
    var defaultImg: String? = null
) : Entity() {

    private val daysBetweenDates: Int
        get() = 0

    override fun toString(): String {
        return "Cycle{" +
                "id= " + id +
                ", coment= " + comment +
                ", title= " + title +
                ", startDate=" + startStringFormatDate +
                ", finishDate=" + finishStringFormatDate +
                ", userList=" + workoutList.size +
                ", image=" + image +
                ", defaultImg=" + defaultImg +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Cycle) return false
        val cycle = o
        if (id != cycle.id) return false
        if (title != cycle.title) return false
        if (startStringFormatDate != cycle.startStringFormatDate) return false
        return if (finishStringFormatDate != cycle.finishStringFormatDate) false else comment == cycle.comment
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + if (image != null) image.hashCode() else 0
        return result
    }

    companion object {
        fun mapFromDbEntity(entity: CycleEntity): Cycle {
            val cycle = Cycle(
                workoutList = listOf(),
                isDefaultType = entity.default_type == 1,

                defaultImg = entity.default_img
            )
            cycle.title = entity.title
            cycle.id = entity._id
            cycle.setStartDate(entity.start_date)
            cycle.setFinishDate(entity.finish_date)
            cycle.comment = entity.comment!!
            cycle.image = entity.img ?: ""
            cycle.defaultImg = entity.default_img
            return cycle
        }

        fun mapToEntity(cycle: Cycle): CycleEntity {
            val cycleEntity = CycleEntity(cycle.id)
            cycleEntity.title = cycle.title.orEmpty()
            cycleEntity.start_date = cycle.startStringFormatDate
            cycleEntity.finish_date = cycle.finishStringFormatDate
            cycleEntity.comment = cycle.comment
            cycleEntity.img = cycle.image
            cycleEntity.default_img = cycle.defaultImg

            return cycleEntity
        }
        fun copy(cycle: Cycle): Cycle {
            return Cycle().apply {
                id = cycle.id
                title = cycle.title
                isDefaultType = cycle.isDefaultType
                image = cycle.image
                startDate = cycle.startDate
                finishDate = cycle.finishDate
                comment = cycle.comment
                parentId = cycle.parentId
            }
        }
    }
}

