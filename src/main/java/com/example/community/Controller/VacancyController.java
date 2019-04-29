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
// Get a Single Note
@GetMapping("/search/{userID,postID}")
public Vacancy getVacancyById(@PathVariable(value = "postID") Long postId,@PathVariable(value = "userID") Long userId) {
        return vacancyRepo.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        }
@PutMapping("/{postid}")
public Vacancy updateNote(@PathVariable(value = "postid") Long postId, @Valid @RequestBody Vacancy postDetails) {
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
        Vacancy updatedNote = vacancyRepo.save(post);
        return updatedNote;
        }
@DeleteMapping("/{postid,userid}")
public ResponseEntity<?> deletepost(@PathVariable(value = "postid") Long postId,@PathVariable(value = "userid") Long userId) {
        Vacancy post = vacancyRepo.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        vacancyRepo.delete(post);
        return ResponseEntity.ok().build();
        }

        }
