package jmw.rdtv.Model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author William
 */
public class ImageBin extends Media {

    @JsonProperty("images")
    ArrayList<String> images = new ArrayList<>();

    /**
     *
     * @return
     */
    public ArrayList<String> getImages() {
        return images;
    }

    /**
     *
     * @param images
     */
    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    /**
     *
     * @param images
     */
    public void addImage(String images) {
        this.images.add(images);
    }
}
