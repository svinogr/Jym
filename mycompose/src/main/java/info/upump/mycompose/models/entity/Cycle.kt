package info.upump.mycompose.models.entity

import android.util.Log
import info.upump.database.entities.CycleEntity

class Cycle(
    val title: String?,
    var workoutList: List<Workout> = ArrayList(),
    var isDefaultType: Boolean = false,
    var image: String? = null,
    var defaultImg: String? = null
) : Entity() {

    private val daysBetweenDates: Int
        get() = 0

    override fun toString(): String {
        return "Cycle{" +
                "id" + id +
                "com " + comment +
                "title " + title +
                " startDate=" + startStringFormatDate +
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

    companion object{
        fun mapFromDbEntity(entity: CycleEntity) : Cycle {
//            Log.d("TAG", entity.default_img!!)
            val cycle = Cycle(title = entity.title, workoutList = listOf(), isDefaultType = entity.default_type == 1, image = entity.img, defaultImg = entity.default_img )
            cycle.id = entity._id
            cycle.setStartDate(entity.start_date)
            cycle.setFinishDate(entity.finish_date)
            cycle.comment = entity.comment
            cycle.image = entity.img
            cycle.defaultImg = entity.default_img
//            Log.d("TAG", "c ${cycle.defaultImg!!}")
            return cycle
        }
    }
}

