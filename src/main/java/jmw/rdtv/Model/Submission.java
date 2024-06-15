package jmw.rdtv.Model;

import java.util.*;

/**
 *
 * @author hhwl
 */
public class Submission {

    private String name;
    private String description;
    private long begin;
    private long end;
    private Media media;
    private int priority;
    private long submitTime;
    private boolean approved;

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public long getBegin() {
        return begin;
    }

    /**
     *
     * @param begin
     */
    public void setBegin(long begin) {
        this.begin = begin;
    }

    /**
     *
     * @return
     */
    public long getEnd() {
        return end;
    }

    /**
     *
     * @param end
     */
    public void setEnd(long end) {
        this.end = end;
    }

    /**
     *
     * @return
     */
    public Media getMedia() {
        return media;
    }

    /**
     *
     * @param media
     */
    public void setMedia(Media media) {
        this.media = media;
    }

    /**
     *
     * @return
     */
    public int getPriority() {
        return priority;
    }

    /**
     *
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }

    public Submission(String name, String description, long begin, long end, Media media, int priority, long submitTime, boolean approved) {
        this.name = name;
        this.description = description;
        this.begin = begin;
        this.end = end;
        this.media = media;
        this.priority = priority;
        this.submitTime = submitTime;
        this.approved = approved;
    }
}
