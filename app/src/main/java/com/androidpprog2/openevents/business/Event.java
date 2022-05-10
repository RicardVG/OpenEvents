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
    private Date creationDate;
    @SerializedName("start_date")
    private Date startDate;
    @SerializedName("end_date")
    private Date endDate;
    @SerializedName("num_participants")
    private int numParticipants;

    public Event(int id, String name, String location, String description, String image, String type, String comentary, String puntuation, int ownerId, Date creationDate, Date startDate, Date endDate, int numParticipants) {
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

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getNumParticipants() {
        return numParticipants;
    }

    public int getId() {
        return id;
    }
}
