/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package product;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Transient;

/**
 *
 * @author rajes
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@DiscriminatorColumn(name = "Pro_Type")
@Table(name = "PRODUCT")
@NamedQueries({
    @NamedQuery(name = "findAllProducts", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.getProductType", query = "SELECT p FROM Product p WHERE TYPE(p) = :type"),
    @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
    @NamedQuery(name = "Product.findByTitle", query = "SELECT p FROM Product p WHERE p.title = :title"),
    @NamedQuery(name = "Product.findByCompany", query = "SELECT p FROM Product p WHERE p.company = :company"),
    @NamedQuery(name = "Product.updateStock", query = "UPDATE Product p SET p.stockNumber = :stockNumber WHERE p.id = :id"),})

//product entity class 
public class Product implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "TITLE", nullable = false)
    private String title;
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    @Column(name = "COMPANY", nullable = false)
    private String company;
    @Column(name = "PLATFORM", nullable = false)
    private String platform;
    @Column(name = "CLASSIFICATION", nullable = false)
    private String classification;
    @Column(name = "PRICE", nullable = false)
    private float price;
    @Column(name = "STOCKNUMBER", nullable = false)
    private int stockNumber;

    /* Creates a new instance of Product */
    public Product() {
    }

    //create parameterized constructor 
    public Product(String title, String description, String company, String platform, String classification, float price, int stockNumber) {
        this.title = title;
        this.description = description;
        this.company = company;
        this.platform = platform;
        this.classification = classification;
        this.price = price;
        this.stockNumber = stockNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.title;
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return the platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * @param platform the platform to set
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * @return the classification
     */
    public String getClassification() {
        return classification;
    }

    /**
     * @param classification the classification to set
     */
    public void setClassification(String classification) {
        this.classification = classification;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the stockNumber
     */
    public int getStockNumber() {
        return stockNumber;
    }

    /**
     * @param stockNumber the stockNumber to set
     */
    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }

    @Transient
    //get descriminator value
    public String getDecriminatorValue() {
        return this.getClass().getName();
    }
}
