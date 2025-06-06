package swp_project.dna_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import swp_project.dna_service.entity.CategoryPost;
import java.util.List;

public interface CategoryPostRepository extends JpaRepository<CategoryPost, String>, JpaSpecificationExecutor<CategoryPost> {
    List<CategoryPost> findByIsActiveTrue();
    CategoryPost findByCategoryName(String categoryName);
}
