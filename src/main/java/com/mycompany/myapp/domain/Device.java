package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.myapp.domain.enumeration.Type;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Device.
 */
@Entity
@Table(name = "device")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    private Boolean state;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private Type type;

    @ManyToMany(mappedBy = "devices")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Mood> moods = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Device name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isState() {
        return state;
    }

    public Device state(Boolean state) {
        this.state = state;
        return this;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Type getType() {
        return type;
    }

    public Device type(Type type) {
        this.type = type;
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Set<Mood> getMoods() {
        return moods;
    }

    public Device moods(Set<Mood> moods) {
        this.moods = moods;
        return this;
    }

    public Device addMood(Mood mood) {
        this.moods.add(mood);
        mood.getDevices().add(this);
        return this;
    }

    public Device removeMood(Mood mood) {
        this.moods.remove(mood);
        mood.getDevices().remove(this);
        return this;
    }

    public void setMoods(Set<Mood> moods) {
        this.moods = moods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Device device = (Device) o;
        if (device.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), device.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Device{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", state='" + isState() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
