package si.fri.prpo.nakupovanje.dto;

public class ArtikelDto {

    private Integer seznamId;
    private String imeArtikla;

    public ArtikelDto() {}

    public ArtikelDto(Integer seznamId, String imeArtikla) {
        this.seznamId = seznamId;
        this.imeArtikla = imeArtikla;
    }

    public Integer getSeznamId() {
        return seznamId;
    }

    public void setSeznamId(Integer seznamId) {
        this.seznamId = seznamId;
    }

    public String getImeArtikla() {
        return imeArtikla;
    }

    public void setImeArtikla(String imeArtikla) {
        this.imeArtikla = imeArtikla;
    }
}
