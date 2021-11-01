package com.example.demope.dto;
import java.io.Serializable;

public class ArmorDTO implements Serializable {
    public ArmorDTO(String armorId, String classification, String description, boolean status, String timeOfCreate, int defense) {
        this.armorId = armorId;
        this.classification = classification;
        this.description = description;
        this.status = status;
        this.timeOfCreate = timeOfCreate;
        this.defense = defense;
    }

    public String getArmorId() {
        return armorId;
    }

    public void setArmorId(String armorId) {
        this.armorId = armorId;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTimeOfCreate() {
        return timeOfCreate;
    }

    public void setTimeOfCreate(String timeOfCreate) {
        this.timeOfCreate = timeOfCreate;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    private String armorId, classification, description;
    private boolean status;
    private String timeOfCreate;
    private int defense;

}
