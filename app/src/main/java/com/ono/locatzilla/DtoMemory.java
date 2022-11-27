package com.ono.locatzilla;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DtoMemory {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "image_name")
    private String imageName;
    @ColumnInfo(name = "image_path")
    private String imagePath;

    public DtoMemory(String imageName, String imagePath) {
        this.imageName = imageName;
        this.imagePath = imagePath;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
