package keyin.datastructure.datastructure_sprint_api.tree;

import keyin.datastructure.datastructure_sprint_api.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tree_records")
public class TreeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Stores the original numbers the user entered, e.g., "50, 25, 75"
    @Column(columnDefinition = "TEXT")
    private String inputNumbers;

    // @Lob indicates a Large Object, suitable for storing the full JSON string
    @Lob
    @Column(columnDefinition = "TEXT")
    private String jsonTree;

    // Establishes a many-to-one relationship with the User model.
    // Many tree records can belong to one user.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}