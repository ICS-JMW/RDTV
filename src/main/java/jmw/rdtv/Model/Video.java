package jmw.rdtv.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author William
 */
public class Video extends Media {

    @JsonProperty("location")
    String location;

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

}
