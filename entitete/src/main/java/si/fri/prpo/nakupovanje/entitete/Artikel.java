package si.fri.prpo.nakupovanje.entitete;

import javax.persistence.*;
import java.util.List;

@Entity(name = "artikel")
@NamedQueries(
        {
                @NamedQuery(name = "artikel.getAll", query = "SELECT a FROM Artikel a"),
                @NamedQuery(name = "artikel.getAll", query = "SELECT a FROM Artikel a")
        })
public class Artikel {
    // class atributs
    @id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ime_artikla", length = 50, nullable = false, unique = false)
    private String imeArtikla;

    @ManyToOne @JoinColumn(name = "nakupovalni_seznam_id")
    private NakupovalniSeznam nakupovalniSeznam;

    // getters
    public Integer getId() {return this.id;}
    public String getImeArtikla() {return this.imeArtikla;}
    public NakupovalniSeznam getNakupovalniSeznam() {return this.nakupovalniSeznam;}

    // setters
    public void setId(Integer id) {this.id = id;}
    public void setImeArtikla(String imeArtikla) {this.imeArtikla = imeArtikla;}
    public void setNakupovalniSeznam(NakupovalniSeznam nakupovalniSeznam) {this.nakupovalniSeznam = nakupovalniSeznam;}
}
