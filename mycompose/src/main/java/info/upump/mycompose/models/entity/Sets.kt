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
            sets.parentId = entity.parent_id!!

            return sets
        }

        fun mapTontity(newSets: Sets): SetsEntity {
            val sets = SetsEntity(
                _id = newSets.id)
            sets.parent_id = newSets.parentId
            sets.weight = newSets.weight
            sets.reps = newSets.reps
            sets.past_set = newSets.weightPast

            return sets
        }
    }
}