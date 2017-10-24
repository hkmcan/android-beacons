package com.example.user.uyari;


public class Campaign {

    public String id;
    public String title;
    public String message;
    public boolean active;
    public String url;

    public Campaign(String id, String title, String message, boolean active, String url) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.active = active;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Campaign campaign = (Campaign) o;

        if (active != campaign.active) return false;
        if (id != null ? !id.equals(campaign.id) : campaign.id != null) return false;
        if (title != null ? !title.equals(campaign.title) : campaign.title != null) return false;
        if (message != null ? !message.equals(campaign.message) : campaign.message != null)
            return false;
        return url != null ? url.equals(campaign.url) : campaign.url == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
