package com.example.emurgency13.Home;


import com.google.firebase.firestore.DocumentId;

public class HomeListModel {

    @DocumentId
    private String quiz_id;
    private String name, desc, image, tag, visibility;

    public HomeListModel() {}

    public HomeListModel(String quiz_id, String name, String desc, String image, String level, String visibility, long questions) {
        this.quiz_id = quiz_id;
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.tag = tag;
        this.visibility = visibility;
    }

    public String getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTag() {
        return tag;
    }

    public void setLevel(String level) {
        this.tag = level;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

}
