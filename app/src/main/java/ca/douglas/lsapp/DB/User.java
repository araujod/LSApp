package ca.douglas.lsapp.DB;

public class User {
    public final static String CLIENT_TYPE = "C";
    public final static String RESTAURANT_TYPE = "R"; //This type should be changed manually
    private String name;
    private String address;
    private String birthday;
    private String type;//"C" for client and "R" for restaurant manager

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
