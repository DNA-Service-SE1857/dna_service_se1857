package swp_project.dna_service.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@ToString
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;

    String result_type ;
    String result_percentage ;
    String conclusion;
    String result_detail ;
    String result_file ;
    String tested_date ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToMany(mappedBy = "testResult", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    List<Samples> samples;

    @OneToOne
    @JoinColumn(name = "orders_id", nullable = false)
    Orders orders;

}