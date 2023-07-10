package info.upump.mycompose.models.entity

class Exercise(
    var title: String?,
    var typeMuscle: TypeMuscle? = null,
    var isDefaultType: Boolean = false,
    var isTemplate: Boolean = false,
    var setsList: List<Sets> = ArrayList(),
    var descriptionId: Long = 0,
    var exerciseDescription: ExerciseDescription? = null
) : Entity() {

    fun createInfo(): String {
        return "инфо"
    }

    override fun toString(): String {
        return "Exercise{" +
                "template" + isTemplate +
                ", desc " + descriptionId +
                ", typeMuscle=" + typeMuscle +
                ", defaultType=" + isDefaultType +
                ", setsList=" + setsList.size +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", parentId=" + parentId +
                '}'
    }
}