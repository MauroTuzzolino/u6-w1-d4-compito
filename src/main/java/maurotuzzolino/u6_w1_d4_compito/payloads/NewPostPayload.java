package maurotuzzolino.u6_w1_d4_compito.payloads;

public class NewPostPayload {
    private String titolo;
    private String contenuto;
    private int tempoDiLettura;
    private Long authorId;

    public NewPostPayload() {
    }

    public NewPostPayload(String titolo, String contenuto, int tempoDiLettura, Long authorId) {
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.tempoDiLettura = tempoDiLettura;
        this.authorId = authorId;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public int getTempoDiLettura() {
        return tempoDiLettura;
    }

    public void setTempoDiLettura(int tempoDiLettura) {
        this.tempoDiLettura = tempoDiLettura;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "NewPostPayload{" +
                "titolo='" + titolo + '\'' +
                ", contenuto='" + contenuto + '\'' +
                ", tempoDiLettura=" + tempoDiLettura +
                ", authorId=" + authorId +
                '}';
    }
}