package nivida.com.amulyamica;

import java.io.Serializable;

/**
 * Created by SEO on 12/23/2016.
 */

public class Bean_Scroll_Laminate implements Serializable {

    String egallery_sub_id;
    String egallery_sub_image;
    String scroll_laminate_id;
    String subcategory_id;
    String scroll_laminate_name;
    String scroll_laminate_image;

    public Bean_Scroll_Laminate() {
    }

    public Bean_Scroll_Laminate(String egallery_sub_id, String egallery_sub_image, String scroll_laminate_id, String subcategory_id, String scroll_laminate_name, String scroll_laminate_image) {
        this.egallery_sub_id = egallery_sub_id;
        this.egallery_sub_image = egallery_sub_image;
        this.scroll_laminate_id = scroll_laminate_id;
        this.subcategory_id = subcategory_id;
        this.scroll_laminate_name = scroll_laminate_name;
        this.scroll_laminate_image = scroll_laminate_image;
    }

    public String getEgallery_sub_id() {
        return egallery_sub_id;
    }

    public void setEgallery_sub_id(String egallery_sub_id) {
        this.egallery_sub_id = egallery_sub_id;
    }

    public String getEgallery_sub_image() {
        return egallery_sub_image;
    }

    public void setEgallery_sub_image(String egallery_sub_image) {
        this.egallery_sub_image = egallery_sub_image;
    }

    public String getScroll_laminate_id() {
        return scroll_laminate_id;
    }

    public void setScroll_laminate_id(String scroll_laminate_id) {
        this.scroll_laminate_id = scroll_laminate_id;
    }

    public String getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(String subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public String getScroll_laminate_name() {
        return scroll_laminate_name;
    }

    public void setScroll_laminate_name(String scroll_laminate_name) {
        this.scroll_laminate_name = scroll_laminate_name;
    }

    public String getScroll_laminate_image() {
        return scroll_laminate_image;
    }

    public void setScroll_laminate_image(String scroll_laminate_image) {
        this.scroll_laminate_image = scroll_laminate_image;
    }
}
