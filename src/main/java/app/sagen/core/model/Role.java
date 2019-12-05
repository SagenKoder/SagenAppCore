package app.sagen.core.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
