package ch.hallo02.assessments.bank.smoothie.domain;

import ch.hallo02.assessments.bank.smoothie.model.Smoothie;
import ch.hallo02.assessments.bank.smoothie.persistence.SmoothiePersistence;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class SmoothieService {

    private final SmoothiePersistence smoothiePersistence;

    public SmoothieService(
            SmoothiePersistence smoothiePersistence
    ) {
        this.smoothiePersistence = smoothiePersistence;
    }

    public List<Smoothie> getAll() {
        return this.smoothiePersistence.getAll();
    }

    public Optional<Smoothie> getById(String id) {
        return this.smoothiePersistence.getById(id);
    }

    public void update(Smoothie smoothie) {
        if (Objects.isNull(smoothie.id())) {
            this.smoothiePersistence.add(smoothie);
        } else {
            this.smoothiePersistence.update(smoothie);
        }
    }

    public void delete(Smoothie smoothie) {
        this.smoothiePersistence.delete(smoothie);
    }

}
