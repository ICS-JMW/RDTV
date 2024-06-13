package jmw.rdtv.Medium;

public class ImageMedium extends Medium {

    String name;
    String submitTime;
    String description;
    String end;
    char approved = 'u'; // u = unapproved, d = denied, a = approved

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

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return approved + DELIMITER + " " + name + DELIMITER + " " + description + DELIMITER + " " + submitTime + DELIMITER + " " + end;
    }

    public char getApproved() {
        return approved;
    }

    public void setApproved(char approved) {
        this.approved = approved;
    }

}
