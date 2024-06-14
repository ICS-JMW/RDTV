package jmw.rdtv.Model;

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
public class Images extends Media {

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
     * @param type
     */
    public Images(String[] images, String type) {
        for (String e : images) {
            try {
                this.images.add(ImageIO.read(new File(e)));
            } catch (IOException ex) {
                Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.type = type;
    }

}
