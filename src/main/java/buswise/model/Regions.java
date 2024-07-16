package buswise.model;

import javax.persistence.*;

@Entity
@Table(name = "regions")
public class Regions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "region_id")
    private int regionId;
    private String region;

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Regions(int regionId, String region) {
        this.regionId = regionId;
        this.region = region;
    }

    public Regions() {
    }
}
