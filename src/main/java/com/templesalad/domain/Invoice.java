package com.templesalad.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "total")
    private Double total;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @ManyToOne
    private Battery battery;

    @ManyToOne
    private Branch branch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public Invoice total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Location getLocation() {
        return location;
    }

    public Invoice location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Battery getBattery() {
        return battery;
    }

    public Invoice battery(Battery battery) {
        this.battery = battery;
        return this;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public Branch getBranch() {
        return branch;
    }

    public Invoice branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Invoice invoice = (Invoice) o;
        if (invoice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + id +
            ", total='" + total + "'" +
            '}';
    }
}
