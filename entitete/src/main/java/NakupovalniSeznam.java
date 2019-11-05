import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "nakupovalni_seznam")
@NamedQueries(value =
        {
                @NamedQuery(name = "NakupovalniSeznam.getAll", query = "SELECT n FROM NakupovalniSeznam n"),
                @NamedQuery(name = "NakupovalniSeznam.getSeznamForUporabnik",
                        query = "SELECT n FROM NakupovalniSeznam n WHERE n.uporabnik = :uporabnik")
        })
public class NakupovalniSeznam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "opis")
    private String opis;

    @Column(name = "ustvarjen")
    private Instant ustvarjen;

    @ManyToOne
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik;

    @OneToMany(mappedBy = "nakupovalniSeznam")
    private List<Artikel> artikli;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "nakupovalni_seznam_oznaka",
            joinColumns = @JoinColumn(name = "nakupovalni_seznam_id"),
            inverseJoinColumns = @JoinColumn(name = "oznaka_id")
    )
    private List<Oznaka> oznake;

}
