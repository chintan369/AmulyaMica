package nivida.com.amulyamica;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SEO on 10/24/2016.
 */

public class AppPrefs {
    private static final String USER_PREFS = "USER_PREFS";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    private String is_verify = "is_verify";
    private String Userid="";
    private String Refno="";
    private String Categoryid="";
    private String Laminateid="laminate";
    boolean loggedin=false;
    String page="";
    String Category_name="";
    String Egalleryid="";
    String Subscreenid="";
    String descp_page="";
    String ScrollLaminateId="";
    String SubCategoryId="";
    private String fav = "fav";


    public AppPrefs(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
        Refno=appSharedPrefs.getString("Refno","");
        Userid=appSharedPrefs.getString("Userid","");
        Categoryid=appSharedPrefs.getString("Categoryid","");
        Laminateid=appSharedPrefs.getString("Laminateid","");
        loggedin=appSharedPrefs.getBoolean("loggedin",false);
        page=appSharedPrefs.getString("page","");
        descp_page=appSharedPrefs.getString("descp_page","");
        Category_name=appSharedPrefs.getString("Category_name","");
        Egalleryid=appSharedPrefs.getString("Egalleryid","");
        Subscreenid=appSharedPrefs.getString("Subscreenid","");
        ScrollLaminateId=appSharedPrefs.getString("ScrollLaminateId","");
        SubCategoryId=appSharedPrefs.getString("SubCategoryId","");

    }


    public String getUserid() {
        return appSharedPrefs.getString("Userid","");
    }

    public void setUserid(String userid) {
        Userid = userid;
        prefsEditor.putString("Userid",userid).commit();
    }

    public void setis_verify(String is_verify1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(is_verify, is_verify1).commit();

    }

    public String getis_verify () {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(is_verify, "");
    }

    public String getRefno() {
        return appSharedPrefs.getString("Refno","");
    }

    public void setRefno(String refno) {
        Refno = refno;
        prefsEditor.putString("Refno",Refno).commit();
    }

    public String getCategoryid() {
        return appSharedPrefs.getString("Categoryid","");
    }

    public void setCategoryid(String categoryid) {
        Categoryid = categoryid;
        prefsEditor.putString("Categoryid",Categoryid).commit();
    }

    public String getLaminateid() {
        return appSharedPrefs.getString("Laminateid","");
    }

    public void setLaminateid(String laminateid) {
        Laminateid = laminateid;
        prefsEditor.putString("Laminateid",Laminateid).commit();
    }

    public boolean isLoggedin() {
        return appSharedPrefs.getBoolean("loggedin",false);
    }

    public void setLoggedin(boolean loggedin) {
        this.loggedin = loggedin;
        prefsEditor.putBoolean("loggedin",loggedin).commit();
    }

    public String getPage() {
        return  page=appSharedPrefs.getString("page","");
    }

    public void setPage(String page) {
        this.page = page;
        prefsEditor.putString("page",page).commit();
    }

    public String getCategory_name() {
        return  Category_name=appSharedPrefs.getString("Category_name","");
    }

    public void setCategory_name(String Category_name) {
        this.Category_name = Category_name;
        prefsEditor.putString("Category_name",Category_name).commit();
    }

    public String getSubscreenid() {
        return appSharedPrefs.getString("Subscreenid","");
    }

    public void setSubscreenid(String subscreenid) {
        Subscreenid = subscreenid;
        prefsEditor.putString("Subscreenid",Subscreenid).commit();
    }

    public String getEgalleryid() {
        return appSharedPrefs.getString("Egalleryid","");
    }

    public void setEgalleryid(String egalleryid) {
        Egalleryid = egalleryid;
        prefsEditor.putString("Egalleryid",Egalleryid).commit();
    }

    public String getDescp_page() {
        return  descp_page=appSharedPrefs.getString("descp_page","");
    }

    public void setDescp_page(String descp_page) {
        this.descp_page = descp_page;
        prefsEditor.putString("descp_page",descp_page).commit();
    }

    public void setfav(String fav1) {

        // TODO Auto-generated method stub
        prefsEditor.putString(fav, fav1).commit();
    }


    public String getfav() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(fav, "");
    }

    public String getScrollLaminateId() {
        return  ScrollLaminateId=appSharedPrefs.getString("ScrollLaminateId","");
    }

    public void setScrollLaminateId(String scrollLaminateId) {
        ScrollLaminateId = scrollLaminateId;
        prefsEditor.putString("ScrollLaminateId",ScrollLaminateId).commit();
    }

    public String getSubCategoryId() {
        return  SubCategoryId=appSharedPrefs.getString("SubCategoryId","");
    }

    public void setSubCategoryId(String subCategoryId) {
        SubCategoryId = subCategoryId;
        prefsEditor.putString("SubCategoryId",SubCategoryId).commit();
    }
}

