package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Mood.
 */
@Entity
@Table(name = "mood")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mood implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "mood_device",
               joinColumns = @JoinColumn(name="moods_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="devices_id", referencedColumnName="id"))
    private Set<Device> devices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Mood name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public Mood active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public Mood devices(Set<Device> devices) {
        this.devices = devices;
        return this;
    }

    public Mood addDevice(Device device) {
        this.devices.add(device);
        device.getMoods().add(this);
        return this;
    }

    public Mood removeDevice(Device device) {
        this.devices.remove(device);
        device.getMoods().remove(this);
        return this;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mood mood = (Mood) o;
        if (mood.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mood.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mood{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
