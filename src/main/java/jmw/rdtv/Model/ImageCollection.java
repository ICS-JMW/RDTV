package jmw.rdtv.Model;

import java.awt.Image;
import java.util.*;

public class ImageCollection implements Media {

    ArrayList<Image> images = new ArrayList<>();

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public ImageCollection(Image[] images) {
        this.images = (ArrayList<Image>) Arrays.asList(images);
    }

}
