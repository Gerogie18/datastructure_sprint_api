package keyin.datastructure.datastructure_sprint_api.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A simple name field for the user
    @Column(nullable = false, unique = true)
    private String name;
}
