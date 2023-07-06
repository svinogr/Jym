package info.upump.mycompose.models.entity

class Sets : Entity() {
    var weight = 0.0
    var reps = 0
    var weightPast = 0.0
    override var id: Long
        get() = id
        set(id) {
            this.id = id
        }

    override fun toString(): String {
        return "Sets{" +
                "weight=" + weight +
                ", reps=" + reps +
                ", formatDate='" + formatDate + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", parentId=" + parentId +
                '}'
    }
}