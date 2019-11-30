package si.fri.prpo.nakupovanje.dto;

public class UporabnikDto {

    private String ime;
    private String priimek;
    private String email;
    private String uporabniskoIme;
    private String geslo;

    public UporabnikDto() {}

    public UporabnikDto(String ime, String priimek, String email, String uporabniskoIme, String geslo) {
        this.ime = ime;
        this.priimek = priimek;
        this.email = email;
        this.uporabniskoIme = uporabniskoIme;
        this.geslo = geslo;
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
