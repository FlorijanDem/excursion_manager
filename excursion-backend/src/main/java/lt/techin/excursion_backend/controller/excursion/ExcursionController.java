package lt.techin.excursion_backend.controller.excursion;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.techin.excursion_backend.model.Excursion;
import lt.techin.excursion_backend.repository.ExcursionRepository;

@RestController
@RequestMapping("/api/excursions")
public class ExcursionController {
    
    private final ExcursionRepository excursionRepository;

    public ExcursionController(ExcursionRepository excursionRepository) {
        this.excursionRepository = excursionRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Excursion> getExcursion(@PathVariable Integer id) {
        return excursionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Excursion> getAllExcursions() {
        return excursionRepository.findAll();
    }

    @PostMapping
    public Excursion createExcursion(@RequestBody Excursion excursion) {
        return excursionRepository.save(excursion);
    }
}
