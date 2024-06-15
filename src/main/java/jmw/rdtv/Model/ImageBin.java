package jmw.rdtv.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

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
