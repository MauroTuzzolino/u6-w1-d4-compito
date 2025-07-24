package maurotuzzolino.u6_w1_d4_compito.services;

import maurotuzzolino.u6_w1_d4_compito.entities.Author;
import maurotuzzolino.u6_w1_d4_compito.exceptions.BadRequestException;
import maurotuzzolino.u6_w1_d4_compito.exceptions.NotFoundException;
import maurotuzzolino.u6_w1_d4_compito.payloads.NewAuthorPayload;
import maurotuzzolino.u6_w1_d4_compito.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

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
}