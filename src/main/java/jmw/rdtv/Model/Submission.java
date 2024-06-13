package jmw.rdtv.Model;

import java.util.*;

public class Submission {

    private String name;
    private String description;
    private Date begin;
    private Date end;
    private Media media;
    private int priority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Submission(String name, String description, Date begin, Date end, Media media, int priority) {
        this.name = name;
        this.description = description;
        this.begin = begin;
        this.end = end;
        this.media = media;
        this.priority = priority;
    }

}
