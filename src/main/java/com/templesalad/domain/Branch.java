package com.templesalad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Branch.
 */
@Entity
@Table(name = "branch")
public class Branch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NotNull
    @Column(name = "available", nullable = false)
    private Boolean available;

    @OneToMany(mappedBy = "branch", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Stock> stocks = new HashSet<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "branch")
    @JsonIgnore
    private Set<Invoice> invoices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Branch name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Branch latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Branch longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean isAvailable() {
        return available;
    }

    public Branch available(Boolean available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public Branch stocks(Set<Stock> stocks) {
        this.stocks = stocks;
        return this;
    }

    public Branch addStocks(Stock stock) {
        this.stocks.add(stock);
        stock.setBranch(this);
        return this;
    }

    public Branch removeStocks(Stock stock) {
        this.stocks.remove(stock);
        stock.setBranch(null);
        return this;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public User getUser() {
        return user;
    }

    public Branch user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Branch invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Branch addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setBranch(this);
        return this;
    }

    public Branch removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setBranch(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Branch branch = (Branch) o;
        if (branch.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, branch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Branch{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", available='" + available + "'" +
            '}';
    }
}
