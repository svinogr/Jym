package info.upump.jym.entity;

/**
 * Created by explo on 12.03.2018.
 */

public class ExerciseDescription {
    private long id;
    private String description;
    private long parentId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "ExerciseDescription{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
