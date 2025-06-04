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
    Date createdAt = new Date();


    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<PostStatus> posts;

}
