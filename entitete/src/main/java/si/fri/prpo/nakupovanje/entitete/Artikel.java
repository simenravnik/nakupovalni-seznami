package si.fri.prpo.nakupovanje.entitete;

import javax.persistence.*;

@Entity
@Table(name = "artikel")
@NamedQueries(
        {
                @NamedQuery(name = "Artikel.getAll", query = "SELECT a FROM Artikel a"),
                @NamedQuery(name = "Artikel.getArtikelById",
                        query = "SELECT a FROM Artikel a WHERE a.id = :id"),
                @NamedQuery(name = "Artikel.getArtikelByName",
                        query = "SELECT a FROM Artikel a WHERE a.imeArtikla = :imeArtikla"),
                @NamedQuery(name = "Artikel.getArtikelForNakupovalniSeznam",
                        query = "SELECT a FROM Artikel a WHERE a.nakupovalniSeznam = :nakupovalniSeznam")
        })
public class Artikel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime_artikla", length = 50, nullable = false, unique = false)
    private String imeArtikla;

    @ManyToOne
    @JoinColumn(name = "nakupovalni_seznam_id")
    private NakupovalniSeznam nakupovalniSeznam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImeArtikla() {
        return imeArtikla;
    }

    public void setImeArtikla(String imeArtikla) {
        this.imeArtikla = imeArtikla;
    }

    public NakupovalniSeznam getNakupovalniSeznam() {
        return nakupovalniSeznam;
    }

    public void setNakupovalniSeznam(NakupovalniSeznam nakupovalniSeznam) {
        this.nakupovalniSeznam = nakupovalniSeznam;
    }

    @Override
    public String toString() {
        String res = "ProductName: " + this.getImeArtikla() + " (" + this.getId() + ")" + "<br>ListName: " + this.getNakupovalniSeznam().getOpis();
        return res;
    }
}
