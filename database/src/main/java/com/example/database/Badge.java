package com.example.database;

public class Badge {
    private String title="";
    private String description="";
    private String badgeURL="";

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

    public String getBadgeUrl() {
        return badgeURL;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeURL = badgeUrl;
    }

    public Badge (String badgeTitle, String badgeDescription, String imageurl) {
        this.title = badgeTitle;
        this.description = badgeDescription;
        this.badgeURL = imageurl;
    }
}

