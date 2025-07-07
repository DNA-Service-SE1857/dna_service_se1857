package swp_project.dna_service.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
    public class OrderParticipants {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        String id ;

        String participant_name;
        String relationship;
        int age ;
        String note ;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "orders_id", nullable = false)
        Orders orders;

        @OneToOne(mappedBy = "orderParticipants", cascade = CascadeType.ALL)
        SampleKits sampleKits;
    }
