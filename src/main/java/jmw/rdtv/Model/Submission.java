package jmw.rdtv.Model;

/**
 *
 * @author hhwl
 */
public class Submission {

    private String name;            // event headline
    private String description;     // event long description
    private long begin;             // event begin time (unix timestamp)
    private long end;               // event end time (unix timestamp)
    private Media media;            // media object
    private int priority;           // how often event gets shown by the GUI (keep at 1 for now)
    private long submitTime;        // when this was submitted
    private boolean approved;       // whether or not this has been approved

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
