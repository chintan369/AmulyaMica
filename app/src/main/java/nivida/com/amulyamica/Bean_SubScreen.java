package nivida.com.amulyamica;

import java.io.Serializable;

/**
 * Created by SEO on 12/13/2016.
 */

public class Bean_SubScreen implements Serializable {
    String sub_id;
    String egallery_id;
    String sub_images;
    String sub_screen_name;

    public Bean_SubScreen() {
    }

    public Bean_SubScreen(String sub_id, String egallery_id, String sub_images, String sub_screen_name) {
        this.sub_id = sub_id;
        this.egallery_id = egallery_id;
        this.sub_images = sub_images;
        this.sub_screen_name = sub_screen_name;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getEgallery_id() {
        return egallery_id;
    }

    public void setEgallery_id(String egallery_id) {
        this.egallery_id = egallery_id;
    }

    public String getSub_images() {
        return sub_images;
    }

    public void setSub_images(String sub_images) {
        this.sub_images = sub_images;
    }

    public String getSub_screen_name() {
        return sub_screen_name;
    }

    public void setSub_screen_name(String sub_screen_name) {
        this.sub_screen_name = sub_screen_name;
    }
}
