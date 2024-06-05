package br.com.pbattistella.project.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = -4226952857972427428L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    private Date startDate;

    private Date endDate;

    @OneToOne
    @JoinColumn(name="customer",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "customer_fk_in_activity"))
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project",
                referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "project_fk_in_activity"))
    private Project project;

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) && Objects.equals(name, activity.name) && Objects.equals(description, activity.description) && Objects.equals(startDate, activity.startDate) && Objects.equals(endDate, activity.endDate) && Objects.equals(customer, activity.customer) && Objects.equals(project, activity.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, startDate, endDate, customer, project);
    }
}
