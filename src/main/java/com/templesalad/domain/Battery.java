package com.templesalad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.templesalad.domain.enumeration.Vehicle;

/**
 * A Battery.
 */
@Entity
@Table(name = "battery")
public class Battery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private Vehicle type;

    @NotNull
    @Column(name = "model", nullable = false)
    private String model;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @OneToMany(mappedBy = "battery")
    @JsonIgnore
    private Set<Stock> stocks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getType() {
        return type;
    }

    public Battery type(Vehicle type) {
        this.type = type;
        return this;
    }

    public void setType(Vehicle type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public Battery model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public Battery price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Battery name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public Battery image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Battery imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public Battery stocks(Set<Stock> stocks) {
        this.stocks = stocks;
        return this;
    }

    public Battery addStock(Stock stock) {
        this.stocks.add(stock);
        stock.setBattery(this);
        return this;
    }

    public Battery removeStock(Stock stock) {
        this.stocks.remove(stock);
        stock.setBattery(null);
        return this;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Battery battery = (Battery) o;
        if (battery.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, battery.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Battery{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", model='" + model + "'" +
            ", price='" + price + "'" +
            ", name='" + name + "'" +
            ", image='" + image + "'" +
            ", imageContentType='" + imageContentType + "'" +
            '}';
    }
}
