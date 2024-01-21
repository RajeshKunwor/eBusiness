/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package product;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author anil
 */
@Entity
@Table(name = "GAME")
@DiscriminatorValue("G")
@NamedQueries({
    @NamedQuery(name = "findAllGames", query = "SELECT g FROM Game g"),
    @NamedQuery(name = "Game.findById", query = "SELECT g FROM Game g WHERE g.id = :id"),
    @NamedQuery(name = "Game.findByTitle", query = "SELECT g FROM Game g WHERE g.title = :title"),
    @NamedQuery(name = "Game.findByCompany", query = "SELECT g FROM Game g WHERE g.company = :company"),})

//Game entity which extends Product entity class 
public class Game extends Product implements Serializable {

    private static long serialVersionUID = 1L;

    @Column(name = "HDVIDEOOUTPUT", nullable = false)
    private String hdVideoOputput;
    @Column(name = "HARDDRIVESPACE", nullable = false)
    private int hardDriveSpace;
    @Column(name = "NOOFPLAYER", nullable = false)
    private int noOfPlayer;

    //create an instace of Game
    public Game() {

    }

    //parameterized Game constructor
    public Game(String title, String description, String company, String platform, String classification, float price, int stockNumber,
            String hdVideoOuput, int hardDriveSpace, int noOfPlayer) {
        super(title, description, company, platform, classification, price, stockNumber);//passing value to super class
        this.hdVideoOputput = hdVideoOuput;
        this.hardDriveSpace = hardDriveSpace;
        this.noOfPlayer = noOfPlayer;

    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        super.toString();
        return this.getTitle();
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    /**
     * @return the hdVideoOputput
     */
    public String getHdVideoOputput() {
        return hdVideoOputput;
    }

    /**
     * @param hdVideoOputput the hdVideoOputput to set
     */
    public void setHdVideoOputput(String hdVideoOputput) {
        this.hdVideoOputput = hdVideoOputput;
    }

    /**
     * @return the hardDriveSpace
     */
    public int getHardDriveSpace() {
        return hardDriveSpace;
    }

    /**
     * @param hardDriveSpace the hardDriveSpace to set
     */
    public void setHardDriveSpace(int hardDriveSpace) {
        this.hardDriveSpace = hardDriveSpace;
    }

    /**
     * @return the noOfPlayer
     */
    public int getNoOfPlayer() {
        return noOfPlayer;
    }

    /**
     * @param noOfPlayer the noOfPlayer to set
     */
    public void setNoOfPlayer(int noOfPlayer) {
        this.noOfPlayer = noOfPlayer;
    }

}
