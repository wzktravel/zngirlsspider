package com.wzktravel.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by wzk on 15/10/13.
 */
public class ZnGirlGallery extends Model<ZnGirlGallery> {

    String id;
    String name;
    String gallery;
    List<String> images;

    public ZnGirlGallery() {
    }

    public ZnGirlGallery(String id, String name, String gallery, List<String> images) {
        this.id = id;
        this.name = name;
        this.gallery = gallery;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
