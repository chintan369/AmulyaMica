package nivida.com.amulyamica;

/**
 * Created by SEO on 12/7/2016.
 */

public class Bean_Stores {
String store_id;
    String branch_name;
    String pincode;
    String address;
    String city;
    String state;
    String latitude;
    String longitude;
    String contact_person;
    String phone;
    String mobile;
    String email_id;
    String mapLink="";

    public Bean_Stores() {
    }

    public Bean_Stores(String store_id, String branch_name, String pincode, String address, String city, String state, String latitude, String longitude, String contact_person, String phone, String mobile, String email_id) {
        this.store_id = store_id;
        this.branch_name = branch_name;
        this.pincode = pincode;
        this.address = address;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contact_person = contact_person;
        this.phone = phone;
        this.mobile = mobile;
        this.email_id = email_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getMapLink() {
        return mapLink;
    }

    public void setMapLink(String mapLink) {
        this.mapLink = mapLink;
    }
}
