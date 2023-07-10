package info.upump.mycompose.models.entity

class Workout(
    var title: String?,
    var isWeekEven: Boolean = false,
    var isDefaultType: Boolean = false,
    var isTemplate: Boolean = false,
    var day: Day? = null,
    var exercises: List<Exercise> = ArrayList()
) : Entity() {

    override fun toString(): String {
        return "Workout{" +
                "id=" + id +
                "com " + comment +
                ", title='" + title + '\'' +
                ", weekEven=" + isWeekEven +
                ", Day=" + day +
                ", defaultType=" + isDefaultType +
                ", cycle Id= " + parentId +
                ", exercises Size=" + exercises.size +
                '}'
    }
}