package info.upump.jym.entity;


import java.util.Objects;

public class ExerciseDescription {
    private long id;
    private String img;
    private String title;
    private String defaultImg;

    public String getDefaultImg() {
        return defaultImg;
    }

    public void setDefaultImg(String defaultImg) {
        this.defaultImg = defaultImg;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseDescription that = (ExerciseDescription) o;
        return id == that.id &&
                Objects.equals(img, that.img) &&
                title.equals(that.title) &&
                Objects.equals(defaultImg, that.defaultImg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, img, title, defaultImg);
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
