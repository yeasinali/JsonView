package com.example.hack.jsonview;

/**
 * Created by HackPC on 3/2/2016.
 */
public class ListRow {
    private String title;
    private String time;
    private String des;
    private String image;
    private String sin_image;

    public ListRow() {
        // TODO Auto-generated constructor stub
    }

    public ListRow(String title, String time, String des,String image,String sin_image) {
        super();
        this.title = title;
        this.time = time;
        this.des = des;
        this.image = image;
        this.sin_image = sin_image;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getTime() {
        return time;
    }

    public void setDes(String des) {
        this.des = des;
    }
    public String getDes() {
        return des;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getSin_image() {
        return sin_image;
    }

    public void setSin_image(String sin_image) {
        this.sin_image = sin_image;
    }
}
