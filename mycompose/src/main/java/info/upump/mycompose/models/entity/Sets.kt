package info.upump.mycompose.models.entity

import androidx.compose.ui.Modifier
import info.upump.database.entities.SetsEntity

class Sets(
    var weight: Double = 0.0,
    var reps: Int = 0,
    var weightPast: Double = 0.0
) : Entity() {

    override fun toString(): String {
        return "Sets{" +
                "weight=" + weight +
                ", reps=" + reps +
                ", formatDate='" + formatDate + '\'' +
                ", id=" + id +
                ", comment='" + comment + '\'' +
                ", parentId=" + parentId +
                '}'
    }

    companion object {
        fun mapFromDbEntity(entity: SetsEntity): Sets {
           val sets =  Sets(
                weight = entity.weight!!,
                reps = entity.reps!!,
                weightPast = entity.past_set!!
            )
            sets.id = entity._id

            return sets
        }
    }
}