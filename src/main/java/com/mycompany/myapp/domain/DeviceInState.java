package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DeviceInState.
 */
@Entity
@Table(name = "device_in_state")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeviceInState implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state")
    private Boolean state;

    @ManyToOne
    private Device device;

    @ManyToMany(mappedBy = "deviceInStates")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Mood> moods = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isState() {
        return state;
    }

    public DeviceInState state(Boolean state) {
        this.state = state;
        return this;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Device getDevice() {
        return device;
    }

    public DeviceInState device(Device device) {
        this.device = device;
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Set<Mood> getMoods() {
        return moods;
    }

    public DeviceInState moods(Set<Mood> moods) {
        this.moods = moods;
        return this;
    }

    public DeviceInState addMood(Mood mood) {
        this.moods.add(mood);
        mood.getDeviceInStates().add(this);
        return this;
    }

    public DeviceInState removeMood(Mood mood) {
        this.moods.remove(mood);
        mood.getDeviceInStates().remove(this);
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
        DeviceInState deviceInState = (DeviceInState) o;
        if (deviceInState.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deviceInState.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceInState{" +
            "id=" + getId() +
            ", state='" + isState() + "'" +
            "}";
    }
}
