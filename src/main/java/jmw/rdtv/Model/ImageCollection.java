package jmw.rdtv.Model;

import java.awt.Image;
import java.util.*;

/**
 *
 * @author hhwl
 */
public class ImageCollection implements Media {

    ArrayList<Image> images = new ArrayList<>();

    /**
     *
     * @return
     */
    public ArrayList<Image> getImages() {
        return images;
    }

    /**
     *
     * @param images
     */
    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    /**
     *
     * @param images
     */
    public ImageCollection(Image[] images) {
        this.images = (ArrayList<Image>) Arrays.asList(images);
    }

}
