package zad_11;

public class WriteRecord {

    private Airlines air;
    private Boolean compressed;

    public WriteRecord(Airlines a, Boolean compressed) {
        this.air = a;
        this.compressed = compressed;
    }

    @Override
    public String toString() {
        return air.toString() + (compressed ? ", compressed" : "");
    }
}
