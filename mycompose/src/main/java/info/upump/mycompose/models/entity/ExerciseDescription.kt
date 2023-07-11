package info.upump.mycompose.models.entity

import java.util.Objects

class ExerciseDescription(
    var id: Long = 0,
    var img: String? = null,
    var title: String? = null,
    var defaultImg: String? = null
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
                ", title='" + title + '\'' +
                '}'
    }
}