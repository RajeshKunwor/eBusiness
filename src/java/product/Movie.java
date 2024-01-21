/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package product;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author anil
 */
@Entity
@Table(name = "MOVIE")
@DiscriminatorValue("M")
@NamedQueries({
    @NamedQuery(name = "findAllMovies", query = "SELECT m FROM Movie m"),
    @NamedQuery(name = "Movie.findById", query = "SELECT m FROM Movie m WHERE m.id = :id"),
    @NamedQuery(name = "Movie.findByTitle", query = "SELECT m FROM Movie m WHERE m.title = :title"),
    @NamedQuery(name = "Movie.findByCompany", query = "SELECT m FROM Movie m WHERE m.company = :company"),})

//Movie entity class which extends Product entity class
public class Movie extends Product implements Serializable {

    @Column(name = "DURATION", nullable = false)
    private int duration;
    @Column(name = "SPECIALFEATURES", nullable = false)
    private String specialFeatures;

    //create constructor
    public Movie() {

    }

    //create parameterized Movie constructor
    public Movie(String title, String description, String company, String platform, String classification, float price, int stockNumber,
            int duration, String specialFeatures) {
        super(title, description, company, platform, classification, price, stockNumber);//passing value to super class
        this.duration = duration;
        this.specialFeatures = specialFeatures;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the specialFeatures
     */
    public String getSpecialFeatures() {
        return specialFeatures;
    }

    /**
     * @param specialFeatures the specialFeatures to set
     */
    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

}
