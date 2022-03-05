package com.example.flickpickbeta;


public class MovieModelClass {


    String title;
    String rating;
    String info;
    String img;

    public MovieModelClass(String title, String rating, String info, String img){
        this.title=title;
        this.rating= rating;
        this.info= info;
        this.img= img;
    }

    public MovieModelClass() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
