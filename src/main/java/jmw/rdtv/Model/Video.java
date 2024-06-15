package jmw.rdtv.Model;

/**
 *
 * @author hhwl
 */
public class Video extends Media {

    String location;

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    public Video(String location, String type) {
        this.location = location;
        this.type = type;
    }

}
