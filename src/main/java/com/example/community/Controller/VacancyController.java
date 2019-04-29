package com.example.community.Controller;

import com.example.community.model.Vacancy;
import com.example.community.Exception.ResourceNotFoundException;
import com.example.community.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts/vacancy")
public class VacancyController {

    @Autowired
    VacancyRepository vacancyRepo;

    @GetMapping("/all")
    public List<Vacancy> getAllPosts() {
        return vacancyRepo.findAll();
    }

    @PostMapping("/add")
    public Vacancy createNote(@Valid @RequestBody Vacancy post) {
        return vacancyRepo.save(post);
    }

    @GetMapping("/search/{userID,postID}")
    public Vacancy getVacancyById(@PathVariable(value = "postID") Long postId, @PathVariable(value = "userID") Long userId) {
        return vacancyRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    @PutMapping("/{postid}")
    public Vacancy updatevacancy(@PathVariable(value = "postid") Long postId, @Valid @RequestBody Vacancy postDetails) {
        Vacancy post = vacancyRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        post.setDescreption(postDetails.getDescreption());
        post.setAppid(postDetails.getAppid());
        post.setBenfits(postDetails.getBenfits());
        post.setPosterID(postDetails.getPosterID());
        post.setPosterName(postDetails.getPosterName());
        post.setRequirments(postDetails.getRequirments());
        post.setTilte(postDetails.getTitle());
        post.setSalary(postDetails.getSalary());
        post.setType(postDetails.getType());
        Vacancy updatedvacancy = vacancyRepo.save(post);
        return updatedvacancy;
    }

    @DeleteMapping("/{postid,companyid}")
    public ResponseEntity<?> deletevacancy(@PathVariable(value = "postid") Long postId, @PathVariable(value = "companyid") String companyId) {
        Vacancy post = vacancyRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        if (post.getPosterID().equals(companyId)) {
            vacancyRepo.delete(post);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{postid,vote}")
        public Vacancy VoteToVacancy(@PathVariable(value = "postid") Long postId,@PathVariable(value = "vote") int vote, @Valid @RequestBody Vacancy postDetails) {
            Vacancy post = vacancyRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
            post.setvote(vote);
            Vacancy updatedVacancy = vacancyRepo.save(post);
            return updatedVacancy;
        }

}
