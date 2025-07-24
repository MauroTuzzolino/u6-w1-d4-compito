package maurotuzzolino.u6_w1_d4_compito.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import maurotuzzolino.u6_w1_d4_compito.entities.Author;
import maurotuzzolino.u6_w1_d4_compito.exceptions.BadRequestException;
import maurotuzzolino.u6_w1_d4_compito.exceptions.NotFoundException;
import maurotuzzolino.u6_w1_d4_compito.payloads.NewAuthorPayload;
import maurotuzzolino.u6_w1_d4_compito.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private Cloudinary cloudinary;

    // 1. Ritorna tutti gli autori
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    // 2. Ritorna un singolo autore per ID
    public Author findById(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Autore con id " + id + " non trovato"));
    }

    // 3. Crea un nuovo autore
    public Author save(NewAuthorPayload payload) {
        if (payload.getNome() == null || payload.getNome().isEmpty())
            throw new BadRequestException("Il nome è obbligatorio");

        if (payload.getCognome() == null || payload.getCognome().isEmpty())
            throw new BadRequestException("Il cognome è obbligatorio");

        if (payload.getEmail() == null || !payload.getEmail().contains("@"))
            throw new BadRequestException("Email non valida");

        if (payload.getDataDiNascita() == null)
            throw new BadRequestException("La data di nascita è obbligatoria");

        Author author = new Author();
        author.setNome(payload.getNome());
        author.setCognome(payload.getCognome());
        author.setEmail(payload.getEmail());
        author.setDataDiNascita(payload.getDataDiNascita());
        author.setAvatar("https://ui-avatars.com/api/?name=" + payload.getNome() + "+" + payload.getCognome());

        return authorRepository.save(author);
    }

    // 4. Modifica un autore esistente
    public Author updateById(long id, NewAuthorPayload payload) {
        Author author = this.findById(id);

        if (payload.getNome() == null || payload.getNome().isEmpty())
            throw new BadRequestException("Il nome è obbligatorio");

        if (payload.getCognome() == null || payload.getCognome().isEmpty())
            throw new BadRequestException("Il cognome è obbligatorio");

        if (payload.getEmail() == null || !payload.getEmail().contains("@"))
            throw new BadRequestException("Email non valida");

        if (payload.getDataDiNascita() == null)
            throw new BadRequestException("La data di nascita è obbligatoria");

        author.setNome(payload.getNome());
        author.setCognome(payload.getCognome());
        author.setEmail(payload.getEmail());
        author.setDataDiNascita(payload.getDataDiNascita());
        author.setAvatar("https://ui-avatars.com/api/?name=" + payload.getNome() + "+" + payload.getCognome());

        return authorRepository.save(author);
    }

    // 5. Elimina un autore
    public void deleteById(long id) {
        Author author = this.findById(id);
        authorRepository.delete(author);
    }

    public Author uploadAvatar(long id, MultipartFile file) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Autore con id " + id + " non trovato"));

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", "authors_avatars"));

            String avatarUrl = (String) uploadResult.get("secure_url");
            author.setAvatar(avatarUrl);
            authorRepository.save(author);
            return author;

        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'upload dell'avatar: " + e.getMessage());
        }
    }
}