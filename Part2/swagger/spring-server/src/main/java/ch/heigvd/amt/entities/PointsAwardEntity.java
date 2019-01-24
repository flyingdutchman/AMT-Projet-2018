package ch.heigvd.amt.entities;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class represent the event of a gain in a pointScaleId
 */
@Entity
public class PointsAwardEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private Long foreignUserId;
    private Long pointScaleId;
    private Integer amount;
    private DateTime timestamp;
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

    public Long getForeignUserId() {
        return foreignUserId;
    }

    public void setForeignUserId(Long foreignUserId) {
        this.foreignUserId = foreignUserId;
    }

    public Long getPointScaleId() {
        return pointScaleId;
    }

    public void setPointScaleId(Long pointScaleId) {
        this.pointScaleId = pointScaleId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }
}
