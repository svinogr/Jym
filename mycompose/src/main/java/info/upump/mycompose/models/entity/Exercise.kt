package info.upump.mycompose.models.entity

class Exercise : Entity() {
    var typeMuscle: TypeMuscle? = null
    var isDefaultType = false
    var isTemplate = false
    var setsList: List<Sets> = ArrayList()
    var descriptionId: Long = 0
    var exerciseDescription: ExerciseDescription? = null
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