package br.com.pbattistella.project.model;

import br.com.pbattistella.project.util.StatusEnum;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 6806651976691246488L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

    private Date startDate;

    private Date endDate;

    @OneToOne
    @JoinColumn(name="manager",
                referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "manager_fk_in_project"))
    private Customer manager;

    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "project",
               cascade = CascadeType.ALL)
    private List<Activity> activities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Customer getManager() {
        return manager;
    }

    public void setManager(Customer manager) {
        this.manager = manager;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) && Objects.equals(name, project.name) && Objects.equals(description, project.description) && status == project.status && Objects.equals(startDate, project.startDate) && Objects.equals(endDate, project.endDate) && Objects.equals(manager, project.manager) && Objects.equals(activities, project.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, startDate, endDate, manager, activities);
    }
}
