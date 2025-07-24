package maurotuzzolino.u6_w1_d4_compito.runners;

import maurotuzzolino.u6_w1_d4_compito.entities.Author;
import maurotuzzolino.u6_w1_d4_compito.entities.Post;
import maurotuzzolino.u6_w1_d4_compito.repositories.AuthorRepository;
import maurotuzzolino.u6_w1_d4_compito.repositories.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

//@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(AuthorRepository authorRepository, PostRepository postRepository) {
        return args -> {
            if (authorRepository.count() == 0 && postRepository.count() == 0) {
                Author a1 = new Author(0, "Mario", "Rossi", "mario.rossi@example.com", LocalDate.of(1980, 5, 15), "https://example.com/avatar1.png");
                Author a2 = new Author(0, "Luca", "Bianchi", "luca.bianchi@example.com", LocalDate.of(1990, 7, 20), "https://example.com/avatar2.png");
                Author a3 = new Author(0, "Giulia", "Verdi", "giulia.verdi@example.com", LocalDate.of(1985, 3, 10), "https://example.com/avatar3.png");

                authorRepository.save(a1);
                authorRepository.save(a2);
                authorRepository.save(a3);

                postRepository.save(new Post("Tecnologia", "Contenuto del post 1", 5, "https://example.com/cover1.jpg", a1));
                postRepository.save(new Post("Viaggi", "Contenuto del post 2", 3, "https://example.com/cover2.jpg", a2));
                postRepository.save(new Post("Cucina", "Contenuto del post 3", 4, "https://example.com/cover3.jpg", a3));
                postRepository.save(new Post("Sport", "Contenuto del post 4", 2, "https://example.com/cover4.jpg", a1));

                System.out.println("Database inizializzato con dati di esempio.");
            } else {
                System.out.println("Database già inizializzato, nessuna azione necessaria.");
            }
        };
    }
}