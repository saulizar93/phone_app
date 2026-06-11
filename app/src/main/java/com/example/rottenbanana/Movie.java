package com.example.rottenbanana;

public class Movie {
    private int id;
    private String title;
    private String year;
    private String genre;
    private String description;

    // Constructor for creating a new movie (without ID)
    public Movie(String title, String year, String genre, String description) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.description = description;
    }

    // Constructor for reading a movie (with ID)
    public Movie(int id, String title, String year, String genre, String description) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.description = description;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
