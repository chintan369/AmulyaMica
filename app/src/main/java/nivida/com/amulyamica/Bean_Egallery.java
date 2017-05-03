package nivida.com.amulyamica;

import java.io.Serializable;

/**
 * Created by SEO on 12/13/2016.
 */

public class Bean_Egallery implements Serializable {
    String egallery_id;
    String g_laminate_id;
    String g_finish_id;
    String egallery_name;
    String egallery_image;

    public Bean_Egallery() {
    }

    public Bean_Egallery(String egallery_id, String g_laminate_id, String g_finish_id, String egallery_name, String egallery_image) {
        this.egallery_id = egallery_id;
        this.g_laminate_id = g_laminate_id;
        this.g_finish_id = g_finish_id;
        this.egallery_name = egallery_name;
        this.egallery_image = egallery_image;
    }

    public String getEgallery_id() {
        return egallery_id;
    }

    public void setEgallery_id(String egallery_id) {
        this.egallery_id = egallery_id;
    }

    public String getG_laminate_id() {
        return g_laminate_id;
    }

    public void setG_laminate_id(String g_laminate_id) {
        this.g_laminate_id = g_laminate_id;
    }

    public String getG_finish_id() {
        return g_finish_id;
    }

    public void setG_finish_id(String g_finish_id) {
        this.g_finish_id = g_finish_id;
    }

    public String getEgallery_name() {
        return egallery_name;
    }

    public void setEgallery_name(String egallery_name) {
        this.egallery_name = egallery_name;
    }

    public String getEgallery_image() {
        return egallery_image;
    }

    public void setEgallery_image(String egallery_image) {
        this.egallery_image = egallery_image;
    }
}
