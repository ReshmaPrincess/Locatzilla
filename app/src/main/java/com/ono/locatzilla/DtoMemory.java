package com.ono.locatzilla;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Memory")
public class DtoMemory {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "image_name")
    private String imageName;
    @ColumnInfo(name = "image_path")
    private String imagePath;

    public DtoMemory(int id, String imageName, String imagePath) {
        this.id = id;
        this.imageName = imageName;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
