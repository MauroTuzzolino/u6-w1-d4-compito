package maurotuzzolino.u6_w1_d4_compito.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import maurotuzzolino.u6_w1_d4_compito.entities.Author;
import maurotuzzolino.u6_w1_d4_compito.entities.Post;
import maurotuzzolino.u6_w1_d4_compito.exceptions.BadRequestException;
import maurotuzzolino.u6_w1_d4_compito.exceptions.NotFoundException;
import maurotuzzolino.u6_w1_d4_compito.payloads.NewPostPayload;
import maurotuzzolino.u6_w1_d4_compito.repositories.AuthorRepository;
import maurotuzzolino.u6_w1_d4_compito.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private Cloudinary cloudinary;

    // 1. Lista di tutti i post
//    public List<Post> findAll() {
//        return postRepository.findAll();
//    }

    public Page<Post> getPostsPaginated(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    // 2. Trova un singolo post per ID
    public Post findById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post con id " + id + " non trovato"));
    }

    // 3. Crea un nuovo post
    public Post save(NewPostPayload payload) {
        if (payload.getTitolo() == null || payload.getTitolo().isEmpty())
            throw new BadRequestException("Il titolo non può essere vuoto");

        if (payload.getContenuto() == null || payload.getContenuto().isEmpty())
            throw new BadRequestException("Il contenuto non può essere vuoto");

        if (payload.getTempoDiLettura() <= 0)
            throw new BadRequestException("Il tempo di lettura deve essere maggiore di 0");

        if (payload.getAuthorId() == null)
            throw new BadRequestException("L'ID dell'autore è obbligatorio");

        Author author = authorRepository.findById(payload.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Autore con id " + payload.getAuthorId() + " non trovato"));

        Post post = new Post();
        post.setTitolo(payload.getTitolo());
        post.setContenuto(payload.getContenuto());
        post.setTempoDiLettura(payload.getTempoDiLettura());
        post.setAuthor(author);
        post.setCover("https://picsum.photos/seed/" + payload.getTitolo().replace(" ", "-") + "/600/400");

        return postRepository.save(post);
    }

    // 4. Modifica un post esistente
    public Post updateById(long id, NewPostPayload payload) {
        Post post = this.findById(id);

        if (payload.getTitolo() == null || payload.getTitolo().isEmpty())
            throw new BadRequestException("Il titolo non può essere vuoto");

        if (payload.getContenuto() == null || payload.getContenuto().isEmpty())
            throw new BadRequestException("Il contenuto non può essere vuoto");

        if (payload.getTempoDiLettura() <= 0)
            throw new BadRequestException("Il tempo di lettura deve essere maggiore di 0");

        if (payload.getAuthorId() == null)
            throw new BadRequestException("L'ID dell'autore è obbligatorio");

        Author author = authorRepository.findById(payload.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Autore con id " + payload.getAuthorId() + " non trovato"));

        post.setTitolo(payload.getTitolo());
        post.setContenuto(payload.getContenuto());
        post.setTempoDiLettura(payload.getTempoDiLettura());
        post.setAuthor(author);
        post.setCover("https://picsum.photos/seed/" + payload.getTitolo().replace(" ", "-") + "/600/400");

        return postRepository.save(post);
    }

    // 5. Cancella un post per ID
    public void deleteById(long id) {
        Post post = this.findById(id);
        postRepository.delete(post);
    }

    public Post uploadCover(long id, MultipartFile file) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post con id " + id + " non trovato"));

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", "posts_covers"));

            String coverUrl = (String) uploadResult.get("secure_url");
            post.setCover(coverUrl);
            postRepository.save(post);
            return post;

        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'upload della cover: " + e.getMessage());
        }
    }
}