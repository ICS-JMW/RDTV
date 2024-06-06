package jmw.rdtv.tvapp;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class medium {

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param Description the Description to set
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * @return the Begin
     */
    public String getBegin() {
        return Begin;
    }

    /**
     * @param Begin the Begin to set
     */
    public void setBegin(String Begin) {
        this.Begin = Begin;
    }

    /**
     * @return the End
     */
    public String getEnd() {
        return End;
    }

    /**
     * @param End the End to set
     */
    public void setEnd(String End) {
        this.End = End;
    }
    
    private String Name;
    private String Description;
    private String Begin;
    private String End;
  
    public void draw(Graphics g){}
    
}
