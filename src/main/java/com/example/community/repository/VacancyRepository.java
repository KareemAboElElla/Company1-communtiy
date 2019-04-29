package com.example.community.repository;
import com.example.community.model.UserPost;
import com.example.community.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author Chaos Ruler
 */

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long>{



}
