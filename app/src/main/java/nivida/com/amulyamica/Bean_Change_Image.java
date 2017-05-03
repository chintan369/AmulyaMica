package nivida.com.amulyamica;

/**
 * Created by SEO on 12/23/2016.
 */

public class Bean_Change_Image {
    String laminateID;
    String laminateImage;

    public Bean_Change_Image() {
    }

    public Bean_Change_Image(String laminateID, String laminateImage) {
        this.laminateID = laminateID;
        this.laminateImage = laminateImage;
    }

    public String getLaminateID() {
        return laminateID;
    }

    public void setLaminateID(String laminateID) {
        this.laminateID = laminateID;
    }

    public String getLaminateImage() {
        return laminateImage;
    }

    public void setLaminateImage(String laminateImage) {
        this.laminateImage = laminateImage;
    }
}
