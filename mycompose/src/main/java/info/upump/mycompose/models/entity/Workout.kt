package info.upump.mycompose.models.entity

class Workout : Entity() {
    var isWeekEven = false
    var isDefaultType = false
    var isTemplate = false
    var day: Day? = null
    var exercises: List<Exercise> = ArrayList()
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