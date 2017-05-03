package nivida.com.amulyamica.models;

import java.io.Serializable;

/**
 * Created by Nivida new on 18-Feb-17.
 */

public class CertificateItem implements Serializable {

    String id="";
    String title="";
    String image="";

    public CertificateItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
