package si.fri.prpo.nakupovanje.entitete;

import javax.persistence.*;

@Entity
@Table(name = "artikel")
@NamedQueries(
        {
                @NamedQuery(name = "artikel.getAll", query = "SELECT a FROM Artikel a")
        })
public class Artikel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ime_artikla", length = 50, nullable = false, unique = false)
    private String imeArtikla;

    @ManyToOne
    @JoinColumn(name = "nakupovalni_seznam_id")
    private NakupovalniSeznam nakupovalniSeznam;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
