package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import swp_project.dna_service.entity.PostStatus;

import java.util.List;

public interface PostStatusRepository extends JpaRepository<PostStatus, String>, JpaSpecificationExecutor<PostStatus> {
    List<PostStatus> findByUserId(String userId) ;
}