package info.upump.mycompose.models.entity

import info.upump.database.entities.ExerciseDescriptionEntity
import java.util.Objects

class ExerciseDescription(
    var id: Long = 0,
    var img: String = "",
    var title: String = "",
    var defaultImg: String = ""
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ExerciseDescription
        return img == that.img && title == that.title && defaultImg == that.defaultImg
    }

    //без ID чтобы правильно сравнивать обьекты из текущей базы и базы бекапа
    override fun hashCode(): Int {
        return Objects.hash(img, title, defaultImg)
    }

    override fun toString(): String {
        return "ExerciseDescription{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", title='" + title + " $defaultImg}"
    }

    companion object {
        fun mapFromDbEntity(entity: ExerciseDescriptionEntity): ExerciseDescription {
            val exerciseDescription = ExerciseDescription(entity._id)
            exerciseDescription.img = entity.img ?: ""
            exerciseDescription.defaultImg = entity.default_img!!
            exerciseDescription.title = entity.title!!

            return exerciseDescription
        }
    }
}