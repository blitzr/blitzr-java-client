package com.blitzr.models.tag;

public class Tag {
    private int weight;
    private int position;
    private String name;
    private String slug;
    private TagDescription description;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public TagDescription getDescription() {
        return description;
    }

    public void setDescription(TagDescription description) {
        this.description = description;
    }
}

