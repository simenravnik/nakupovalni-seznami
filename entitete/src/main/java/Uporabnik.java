import javax.persistence.*;

@Entity(name="student")
@NamedQueries(value =
        {
                @NamedQuery(name = "Opomnik.getAll", query = "SELECT u FROM uporabnik u")
                // TODO: se tri smiselne poizvedbe
        })
public class Uporabnik {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="ime", length=50, nullable=false, unique=false)
    private String ime;

    @Column(name="priimek", length=50, nullable=false, unique=false)
    private String priimek;

    @Column(name="email", length=50, nullable=false, unique=false)
    private String email;

    @Column(name="uporabniskoime", length=50, nullable=false, unique=false)
    private String uporabniskoIme;

    @Column(name="geslo", length=50, nullable=false, unique=false)
    private String geslo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUporabniskoIme() {
        return uporabniskoIme;
    }

    public void setUporabniskoIme(String uporabniskoIme) {
        this.uporabniskoIme = uporabniskoIme;
    }

    public String getGeslo() {
        return geslo;
    }

    public void setGeslo(String geslo) {
        this.geslo = geslo;
    }
}
