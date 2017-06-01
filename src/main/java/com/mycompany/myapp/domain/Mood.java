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

    @Column(name = "audio_volume")
    private Integer audioVolume;

    @Column(name = "brightness")
    private Integer brightness;

    @Column(name = "shutter_height")
    private Integer shutterHeight;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "mood_device_in_state",
               joinColumns = @JoinColumn(name="moods_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="device_in_states_id", referencedColumnName="id"))
    private Set<DeviceInState> deviceInStates = new HashSet<>();

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

    public Integer getAudioVolume() {
        return audioVolume;
    }

    public Mood audioVolume(Integer audioVolume) {
        this.audioVolume = audioVolume;
        return this;
    }

    public void setAudioVolume(Integer audioVolume) {
        this.audioVolume = audioVolume;
    }

    public Integer getBrightness() {
        return brightness;
    }

    public Mood brightness(Integer brightness) {
        this.brightness = brightness;
        return this;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public Integer getShutterHeight() {
        return shutterHeight;
    }

    public Mood shutterHeight(Integer shutterHeight) {
        this.shutterHeight = shutterHeight;
        return this;
    }

    public void setShutterHeight(Integer shutterHeight) {
        this.shutterHeight = shutterHeight;
    }

    public Set<DeviceInState> getDeviceInStates() {
        return deviceInStates;
    }

    public Mood deviceInStates(Set<DeviceInState> deviceInStates) {
        this.deviceInStates = deviceInStates;
        return this;
    }

    public Mood addDeviceInState(DeviceInState deviceInState) {
        this.deviceInStates.add(deviceInState);
        deviceInState.getMoods().add(this);
        return this;
    }

    public Mood removeDeviceInState(DeviceInState deviceInState) {
        this.deviceInStates.remove(deviceInState);
        deviceInState.getMoods().remove(this);
        return this;
    }

    public void setDeviceInStates(Set<DeviceInState> deviceInStates) {
        this.deviceInStates = deviceInStates;
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
            ", audioVolume='" + getAudioVolume() + "'" +
            ", brightness='" + getBrightness() + "'" +
            ", shutterHeight='" + getShutterHeight() + "'" +
            "}";
    }
}
