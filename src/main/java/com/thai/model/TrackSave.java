package com.thai.model;

import org.springframework.web.multipart.MultipartFile;

public class TrackSave {
    private Long id;
    private String name;
    private MultipartFile link;

    public TrackSave(String name, MultipartFile link) {
        this.name = name;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getLink() {
        return link;
    }

    public void setLink(MultipartFile link) {
        this.link = link;
    }

    public TrackSave() {
    }

    public TrackSave(Long id, String name, MultipartFile link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }
}
