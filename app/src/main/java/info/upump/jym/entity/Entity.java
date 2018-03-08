package info.upump.jym.entity;

/**
 * Created by explo on 05.03.2018.
 */

public abstract class Entity {
    protected long id;
    protected String title;
    protected String comment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
