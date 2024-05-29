package co.jammingRules.Servizi._FileUpload.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity (name = "users")
@Table(indexes = {
        @Index(unique = true, name = "email_idx", columnList = "email")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(nullable = false, name = "surname")
    private String surname;
    @Column(nullable = false, name = "email", unique = true)
    private String email;
    @Column(name = "profile picture")
    private String profilePicture;
}