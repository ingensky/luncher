package edu.sky.luncher.domain;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Restaurant extends AbstractBaseEntity {

    private String name;

    @OneToMany
    @JoinTable(
            name = "administrators",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> administrators;


    protected Restaurant() {
    }


    public Restaurant(Long id, String name, String administrationPassword, Set<User> administrators) {
        super(id);
        this.name = name;
        this.administrators = administrators;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
