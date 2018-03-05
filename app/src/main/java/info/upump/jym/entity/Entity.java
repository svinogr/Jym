package info.upump.jym.entity;

/**
 * Created by explo on 05.03.2018.
 */

public abstract class Entity {
    protected long id;
    protected String title;

    protected long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }

    protected String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }
}
