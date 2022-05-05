package com.androidpprog2.openevents.business;

import java.util.Date;

public class Event {
    private int id;
    private String name;
    private String location;
    private String description;
    private String image;
    private String type;
    private String comentary;
    private String puntuation;
    private int ownerId;
    private Date creationDate;
    private Date startDate;
    private Date endDate;
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
}
