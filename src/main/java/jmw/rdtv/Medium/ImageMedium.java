package jmw.rdtv.Medium;

/**
 *
 * @author hhwl
 */
public class ImageMedium extends Medium {

    String name;
    String submitTime;
    String description;
    String end;
    char approved = 'u'; // u = unapproved, d = denied, a = approved
    String fileName;

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
    public String getSubmitTime() {
        return submitTime;
    }

    /**
     *
     * @param submitTime
     */
    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    /**
     *
     * @return
     */
    public String getEnd() {
        return end;
    }

    /**
     *
     * @param end
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return approved + DELIMITER + " " + name + DELIMITER + " " + description + DELIMITER + " " + submitTime + DELIMITER + " " + end + DELIMITER + " " + fileName;
    }

    /**
     *
     * @return
     */
    public char getApproved() {
        return approved;
    }

    /**
     *
     * @param approved
     */
    public void setApproved(char approved) {
        this.approved = approved;
    }

    /**
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
