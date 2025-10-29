package com.tecsup.petclinic.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "types")
public class PetType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private Boolean active;

    @Column(name = "size_category")
    private String sizeCategory;

    @Column(name = "average_lifespan")
    private Integer averageLifespan;

    @Column(name = "care_level")
    private String careLevel;

    // Constructores
    public PetType() {}

    public PetType(Integer id, String name, String description, Boolean active,
                   String sizeCategory, Integer averageLifespan, String careLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.sizeCategory = sizeCategory;
        this.averageLifespan = averageLifespan;
        this.careLevel = careLevel;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public String getSizeCategory() { return sizeCategory; }
    public void setSizeCategory(String sizeCategory) { this.sizeCategory = sizeCategory; }
    public Integer getAverageLifespan() { return averageLifespan; }
    public void setAverageLifespan(Integer averageLifespan) { this.averageLifespan = averageLifespan; }
    public String getCareLevel() { return careLevel; }
    public void setCareLevel(String careLevel) { this.careLevel = careLevel; }

    @Override
    public String toString() {
        return "PetType [id=" + id + ", name=" + name + ", description=" + description +
                ", active=" + active + ", sizeCategory=" + sizeCategory +
                ", averageLifespan=" + averageLifespan + ", careLevel=" + careLevel + "]";
    }
}