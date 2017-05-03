package nivida.com.amulyamica;

import java.io.Serializable;

/**
 * Created by SEO on 10/21/2016.
 */

public class Bean_Categories implements Serializable {
    String category_id="";
    String category_name="";
    private String category_image = new String();

    public Bean_Categories() {
    }

    public Bean_Categories(String category_id, String category_name, String category_image) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_image = category_image;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }
}

