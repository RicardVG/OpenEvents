package com.androidpprog2.openevents.business;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event {
    private int id;

    private String name;
    @SerializedName("location")
    private String location;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private String image;
    @SerializedName("type")
    private String type;
    @SerializedName("comentary")
    private String comentary;
    @SerializedName("puntuation")
    private String puntuation;
    @SerializedName("owner_id")
    private int ownerId;
    @SerializedName("date")
    private String creationDate;
    @SerializedName("eventStart_date")
    private String startDate;
    @SerializedName("eventEnd_date")
    private String endDate;
    @SerializedName("num_participants")
    private int numParticipants;

    public Event(int id, String name, String location, String description, String image, String type, String comentary, String puntuation, int ownerId, String creationDate, String startDate, String endDate, int numParticipants) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.image = image;
        this.type = type;
        this.comentary = comentary;
        this.puntuation = puntuation;
        this.ownerId = ownerId;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numParticipants = numParticipants;
    }

    public Event(String name, String image, String location, String description, String eventStart_date, String eventEnd_date, int n_participators, String type) {
        this.name = name;
        this.image = image;
        this.location = location;
        this.description = description;
        this.startDate = eventStart_date;
        this.endDate = eventEnd_date;
        this.numParticipants= n_participators;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public String getComentary() {
        return comentary;
    }

    public String getPuntuation() {
        return puntuation;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getNumParticipants() {
        return numParticipants;
    }

    public int getId() {
        return id;
    }
}
