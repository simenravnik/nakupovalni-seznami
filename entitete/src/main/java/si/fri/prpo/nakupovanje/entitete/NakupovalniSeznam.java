package si.fri.prpo.nakupovanje.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "nakupovalni_seznam")
@NamedQueries(value =
        {
                @NamedQuery(name = "NakupovalniSeznam.getAll", query = "SELECT n FROM NakupovalniSeznam n"),
                @NamedQuery(name = "NakupovalniSeznam.getSeznamById",
                        query = "SELECT n FROM NakupovalniSeznam n WHERE n.id = :id"),
                @NamedQuery(name = "NakupovalniSeznam.getSeznamByName",
                        query = "SELECT n FROM NakupovalniSeznam n WHERE n.naziv = :naziv"),
                @NamedQuery(name = "NakupovalniSeznam.getSeznamForUporabnik",
                        query = "SELECT n FROM NakupovalniSeznam n WHERE n.uporabnik = :uporabnik")
        })
public class NakupovalniSeznam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "opis")
    private String opis;

    @Column(name = "ustvarjen")
    private Instant ustvarjen;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik;

    @OneToMany(mappedBy = "nakupovalniSeznam", cascade = CascadeType.ALL)
    private List<Artikel> artikli;

    public void addArtikel(Artikel artikel) {
        artikli.add(artikel);
    }

    public void updateArtikel(Long artikelId, Artikel artikel) {
        for (Artikel a : artikli) {
            if (a.getId() == artikelId) {
                artikli.set(artikli.indexOf(a), artikel);
                break;
            }
        }
    }

    public void removeArtikel(Artikel artikel) {
        artikli.remove(artikel);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Instant getUstvarjen() {
        return ustvarjen;
    }

    public void setUstvarjen(Instant ustvarjen) {
        this.ustvarjen = ustvarjen;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public List<Artikel> getArtikli() {
        return artikli;
    }

    public void setArtikli(List<Artikel> artikli) {
        this.artikli = artikli;
    }

    @Override
    public String toString() {
        String res = "---" + this.getNaziv() + " (" + this.getId() + ") <br>";
        return res;
    }
}
