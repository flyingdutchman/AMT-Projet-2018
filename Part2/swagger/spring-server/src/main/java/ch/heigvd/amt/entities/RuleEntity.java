package ch.heigvd.amt.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class RuleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private String type;
    private Long awardBadge;
    private Long pointScale;
    private Integer amount;
    private Long applicationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAwardBadge() {
        return awardBadge;
    }

    public void setAwardBadge(Long awardBadge) {
        this.awardBadge = awardBadge;
    }

    public Long getPointScale() {
        return pointScale;
    }

    public void setPointScale(Long pointScale) {
        this.pointScale = pointScale;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }
}
