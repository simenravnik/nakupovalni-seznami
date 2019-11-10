package si.fri.prpo.nakupovanje.entitete;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "uporabnik")
@NamedQueries(value =
        {
                @NamedQuery(name = "Uporabnik.getAll", query = "SELECT u FROM Uporabnik u"),
                @NamedQuery(name = "Uporabnik.getUporabnikById",
                        query = "SELECT u FROM Uporabnik u WHERE u.id = :id"),
                @NamedQuery(name = "Uporabnik.getUporabnikByUsername",
                        query = "SELECT u FROM Uporabnik u WHERE u.uporabniskoIme = :uporabniskoIme"),
                @NamedQuery(name = "Uporabnik.getUporabnikByEmail",
                        query = "SELECT u FROM Uporabnik u WHERE u.email = :email")
        })
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="ime", length=50, nullable=false, unique=false)
    private String ime;

    @Column(name="priimek", length=50, nullable=false, unique=false)
    private String priimek;

    @Column(name="email", length=50, nullable=false, unique=false)
    private String email;

    @Column(name="uporabnisko_ime", length=50, nullable=false, unique=true)
    private String uporabniskoIme;

    @Column(name="geslo", length=50, nullable=false, unique=false)
    private String geslo;

    @OneToMany(mappedBy = "uporabnik", cascade = CascadeType.ALL)
    private List<NakupovalniSeznam> nakupovalniSeznami;

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

    public List<NakupovalniSeznam> getNakupovalniSeznami() {
        return nakupovalniSeznami;
    }

    public void setNakupovalniSeznami(List<NakupovalniSeznam> nakupovalniSeznami) {
        this.nakupovalniSeznami = nakupovalniSeznami;
    }

    @Override
    public String toString() {
        String res = this.getIme() + " " + this.getPriimek() + "(" + this.getId() + ") <br>";
        return res;
    }
}
