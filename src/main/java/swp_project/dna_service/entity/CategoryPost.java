package swp_project.dna_service.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class CategoryPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id;
    String categoryName;
    Boolean isActive;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_at")
    Date updatedAt = new Date();


    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<PostStatus> posts;

}
