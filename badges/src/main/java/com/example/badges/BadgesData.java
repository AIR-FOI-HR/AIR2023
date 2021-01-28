package com.example.badges;

import androidx.annotation.NonNull;

public class BadgesData {

    private String title;
    private String description;
    private String titleeng;
    private String descriptioneng;
    private String badgeURL;

    public BadgesData (String badgeTitle, String badgeTitleeng, String badgeDescription, String badgeDescriptioneng , String imageurl) {
        this.title = badgeTitle;
        this.titleeng = badgeTitleeng;
        this.description = badgeDescription;
        this.descriptioneng = badgeDescriptioneng;
        this.badgeURL = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitleeng() {
        return titleeng;
    }

    public void setTitleeng(String titleeng) {
        this.titleeng = titleeng;
    }

    public String getDescriptioneng() {
        return descriptioneng;
    }

    public void setDescriptioneng(String descriptioneng) {
        this.descriptioneng = descriptioneng;
    }

    public String getBadgeURL() {
        return badgeURL;
    }

    public void setBadgeURL(String badgeURL) {
        this.badgeURL = badgeURL;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
