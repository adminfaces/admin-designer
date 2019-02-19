package com.github.adminfaces.showcase.bean;

import com.github.adminfaces.showcase.model.Entity;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.*;

@Named
@ViewScoped
public class SamplePageMB implements Serializable {

    private List<String> cities;
    private List<String> talks;
    private Entity entity;

    @PostConstruct
    public void init() {
        cities = Arrays.asList("São Paulo", "New York", "Tokyo", "Islamabad", "Chongqing", "Guayaquil", "Porto Alegre", "Hanoi", "Montevideo", "Shijiazhuang", "Guadalajara","Stockholm",
                "Seville", "Moscow", "Glasgow", "Reykjavik", "Lyon", "Barcelona", "Kieve", "Vilnius", "Warsaw", "Budapest", "Prague", "Sofia", "Belgrade");
        talks = Arrays.asList("Designing for Modularity with Java 9", "Twelve Ways to Make Code Suck Less", "Confessions of a Java Educator: 10 Java Insights I Wish I’d Had Earlier",
                "Ten Simple Rules for Writing Great Test Cases", "No more mocks, only real tests with Arquillian", "Cloud native Java with JakartaEE and MicroProfile","Jenkins Essentials: an evergreen version of Jenkins",
                "From Java EE to Jakarta EE: a user perspective", "Cloud Native Java with Open J9, Fast, Lean and Definitely Mean", "Service Mesh and Sidecars with Istio and Envoy");
        cities.sort(Comparator.naturalOrder());
        talks.sort(Comparator.naturalOrder());
        entity = new Entity();
    }


    public void reset() {
        entity = new Entity();
    }

    public void remove() {
        Messages.create("Info").detail("Entity removed successfully.").add();
        reset();
    }

    public void save() {
        Messages.create("Info").detail(String.format("Entity %s successfully.",isNew() ? "created":"updated")).add();
        if(isNew()) {
            entity.setId(new Random(Instant.now().getEpochSecond()).nextLong());
        }
    }

    public List<String> completeTalk(String query) {
        List<String> result = new ArrayList<>();
        talks.stream().filter(t -> t.contains(query))
                       .forEach(result::add);
        return result;
    }

    public Boolean isNew() {
        return entity.getId() == null;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<String> getTalks() {
        return talks;
    }

    public void setTalks(List<String> talks) {
        this.talks = talks;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
