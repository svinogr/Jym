package info.upump.jym.entity;

/**
 * Created by explo on 18.04.2018.
 */

public class ExerciseDescription {
    private long id;
    private String img;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "ExerciseDescription{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
