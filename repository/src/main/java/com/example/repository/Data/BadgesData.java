package com.example.repository.Data;

import androidx.annotation.NonNull;

public class BadgesData {

    private String title;
    private String description;
    private String badgeURL;

    public BadgesData (String badgeTitle, String badgeDescription, String imageurl) {
        this.title = badgeTitle;
        this.description = badgeDescription;
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

    public String getBadgeUrl() {
        return badgeURL;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeURL = badgeUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
