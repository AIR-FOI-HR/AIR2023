package com.example.database;

public class Badge {
    private String title="";
    private String description="";
    private String badgeURL="";
    private String titleeng;
    private String descriptioneng;

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

    public String getBadgeURL() {
        return badgeURL;
    }

    public void setBadgeURL(String badgeURL) {
        this.badgeURL = badgeURL;
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

    public Badge (String badgeTitle, String badgeTitleeng, String badgeDescription, String badgeDescriptioneng , String imageurl) {
        this.title = badgeTitle;
        this.titleeng = badgeTitleeng;
        this.description = badgeDescription;
        this.descriptioneng = badgeDescriptioneng;
        this.badgeURL = imageurl;
    }
}

