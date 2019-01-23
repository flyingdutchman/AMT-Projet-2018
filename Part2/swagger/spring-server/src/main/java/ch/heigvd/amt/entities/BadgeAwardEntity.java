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

    private Long userId;
    private Long badgeId;
    private String apiKey;
    private DateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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
