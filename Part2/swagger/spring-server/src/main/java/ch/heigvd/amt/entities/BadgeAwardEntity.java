package ch.heigvd.amt.entities;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class represents the event a new won badge
 */
@Entity
public class BadgeAwardEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private Long foreignUserId;
    private Long badgeId;
    private Long applicationId;
    private DateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getForeignUserId() {
        return foreignUserId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setForeignUserId(Long foreignUserId) {
        this.foreignUserId = foreignUserId;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }
}
