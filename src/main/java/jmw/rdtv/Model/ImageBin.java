package jmw.rdtv.Model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public void addImage(String images) {
        this.images.add(images);
    }
}
