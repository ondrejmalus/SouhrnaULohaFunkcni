public class Pole {
    private String jmeno;
    private boolean vlastniHru;
    private int hodnoceni;

    public Pole(String jmeno, boolean vlastniHru, int hodnoceni) {
        this.jmeno = jmeno;
        this.vlastniHru = vlastniHru;
        this.hodnoceni = hodnoceni;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public boolean isVlastniHru() {
        return vlastniHru;
    }

    public void setVlastniHru(boolean vlastniHru) {
        this.vlastniHru = vlastniHru;
    }

    public int getHodnoceni() {
        return hodnoceni;
    }

    public void setHodnoceni(int hodnoceni) {
        this.hodnoceni = hodnoceni;
    }
}
