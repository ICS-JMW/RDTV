package jmw.rdtv.Model;

import java.util.*;

public class Submission {

    private String name;
    private String description;
    private long begin;
    private long end;
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

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
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

    public Submission(String name, String description, long begin, long end, Media media, int priority) {
        this.name = name;
        this.description = description;
        this.begin = begin;
        this.end = end;
        this.media = media;
        this.priority = priority;
    }

}
