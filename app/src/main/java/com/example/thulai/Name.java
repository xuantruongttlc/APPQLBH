package com.example.thulai;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "name")
public class Name extends MatHang implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String sotienno;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSotienno() {
        return sotienno;
    }

    public void setSotienno(String sotienno) {
        this.sotienno = sotienno;
    }

    public Name(String name, String sotienno) {
        super();
        this.name = name;
        this.sotienno = sotienno;
    }


}
