package edu.sky.luncher.domain;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table
public class Restaurant extends AbstractBaseEntity {

    private String name;

    private String administrationPassword;


    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private Set<User> administrators;


    protected Restaurant() {
    }


    public Restaurant(Long id, String name, String administrationPassword, Set<User> administrators) {
        super(id);
        this.name = name;
        this.administrationPassword = administrationPassword;
        this.administrators = administrators;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdministrationPassword() {
        return administrationPassword;
    }

    public void setAdministrationPassword(String administrationPassword) {
        this.administrationPassword = administrationPassword;
    }

    public Set<User> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(Set<User> administrators) {
        this.administrators = administrators;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", administrators=" + administrators +
                ", id=" + id +
                '}';
    }
}
