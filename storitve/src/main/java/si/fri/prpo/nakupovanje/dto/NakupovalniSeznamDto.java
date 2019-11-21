package si.fri.prpo.nakupovanje.dto;

public class NakupovalniSeznamDto {

    private Long uporabnikId;

    private String naziv;

    private String opis;

    public NakupovalniSeznamDto() {
    }

    public NakupovalniSeznamDto(Long uporabnikId, String naziv, String opis) {
        this.uporabnikId = uporabnikId;
        this.naziv = naziv;
        this.opis = opis;
    }

    public Long getUporabnikId() {
        return uporabnikId;
    }

    public void setUporabnikId(Long uporabnikId) {
        this.uporabnikId = uporabnikId;
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
}
