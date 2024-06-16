package jmw.rdtv.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;

/**
 *
 * @author hhwl
 */
public class ImageBin extends Media {

    @JsonProperty("images")
    ArrayList<String> images = new ArrayList<>();

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
