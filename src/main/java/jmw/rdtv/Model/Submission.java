package jmw.rdtv.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hhwl
 */
public class Submission {

    private static ObjectMapper mapper = new ObjectMapper();
    public static String DATA_LOC = "./data.json";

    private String headline;        // event headline
    private String description;     // event long description
    private String start;           // event begin time (unix timestamp)
    private String end;             // event end time (unix timestamp)
    private Media media;            // media object
    private String submitTime;      // when this was submitted
    private boolean approved;       // whether or not this has been approved
    private long id;                // submission number, only used for adminpanel
    private boolean hidden = false; // whether to appear on adminpanel

    /**
     *
     */
    public Submission() {

    }

    /**
     *
     * @param headline
     * @param description
     * @param start
     * @param end
     * @param submitTime
     */
    public Submission(String headline, String description, String start, String end, String submitTime) {
        this.headline = headline;
        this.description = description;
        this.start = start;
        this.end = end;
        this.submitTime = submitTime;
    }

    /**
     *
     * @param headline
     * @param description
     * @param start
     * @param end
     * @param media
     * @param submitTime
     * @param approved
     */
    public Submission(String headline, String description, String start, String end, Media media, String submitTime, boolean approved) {
        this.headline = headline;
        this.description = description;
        this.start = start;
        this.end = end;
        this.media = media;
        this.submitTime = submitTime;
        this.approved = approved;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Submission> readSubmissionsFile() {
        try {

            ArrayList<Submission> temp = mapper.readValue(new File(DATA_LOC), new TypeReference<ArrayList<Submission>>() {
            });
            //prevents returning null
            temp = temp != null ? temp : new ArrayList<>();
            return temp;
        } catch (IOException ex) {
            Logger.getLogger(Submission.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("submissions file parsing failed!!!");
            return null;
        }
    }

    /**
     *
     * @param json
     * @return
     */
    public static ArrayList<Submission> readSubmissionsJson(String json) {
        try {
            return mapper.readValue(json, new TypeReference<ArrayList<Submission>>() {
            });
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Submission.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("json parsing failed!!!");
            return null;
        }
    }

    public static void writeSubmissionsFile(ArrayList<Submission> submissionsList) {
        try {
            mapper.writeValue(new File(DATA_LOC), submissionsList);
        } catch (IOException ex) {
            Logger.getLogger(Submission.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public String getHeadline() {
        return headline;
    }

    /**
     *
     * @param headline
     */
    public void setHeadline(String headline) {
        this.headline = headline;
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
    public String getStart() {
        return start;
    }

    /**
     *
     * @param start
     */
    public void setStart(String start) {
        this.start = start;
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
    public boolean isApproved() {
        return approved;
    }

    /**
     *
     * @param approved
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Submission other = (Submission) obj;
        if (this.approved != other.approved) {
            return false;
        }
        if (!Objects.equals(this.headline, other.headline)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.start, other.start)) {
            return false;
        }
        if (!Objects.equals(this.end, other.end)) {
            return false;
        }
        if (!Objects.equals(this.submitTime, other.submitTime)) {
            return false;
        }
        return Objects.equals(this.media, other.media);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.headline);
        hash = 61 * hash + Objects.hashCode(this.description);
        hash = 61 * hash + Objects.hashCode(this.start);
        hash = 61 * hash + Objects.hashCode(this.end);
        hash = 61 * hash + Objects.hashCode(this.media);
        hash = 61 * hash + Objects.hashCode(this.submitTime);
        hash = 61 * hash + (this.approved ? 1 : 0);
        return hash;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static void setMapper(ObjectMapper mapper) {
        Submission.mapper = mapper;
    }

    public static String getDATA_LOC() {
        return DATA_LOC;
    }

    public static void setDATA_LOC(String dATA_LOC) {
        DATA_LOC = dATA_LOC;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
