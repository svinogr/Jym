package info.upump.jym.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public abstract class Entity {
    protected final String formatDate = "yy-mm-dd";
    protected Date startDate;
    protected Date finishDate;
    protected long id;
    protected String title;
    protected String comment;
    protected long parentId;

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
    public Date getStartDate() {
        return startDate;
    }

    public String getStartStringFormatDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        return simpleDateFormat.format(startDate);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        try {
            this.startDate = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public String getFinishStringFormatDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        return simpleDateFormat.format(finishDate);
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setFinishDate(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        try {
            this.finishDate = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;

        Entity entity = (Entity) o;

        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
