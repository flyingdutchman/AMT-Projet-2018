package ch.heigvd.amt.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
public class ForeignUserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private Long version;

    private String applicationUserId;
    private String apiKey;
    private ArrayList<Long> badgeOwned;
    private HashMap<Long, Integer> pointScaleProgress;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(String applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ArrayList<Long> getBadgeOwned() {
        return badgeOwned;
    }

    public void setBadgeOwned(ArrayList<Long> badgeOwned) {
        this.badgeOwned = badgeOwned;
    }

    public HashMap<Long, Integer> getPointScaleProgress() {
        return pointScaleProgress;
    }

    public void setPointScaleProgress(HashMap<Long, Integer> pointScaleProgress) {
        this.pointScaleProgress = pointScaleProgress;
    }

    /**
     *
     * @param badgeId
     * @return Whether or not a badge has been created
     */
    public boolean addOwnedBadge(Long badgeId) {
        if(!badgeOwned.contains(badgeId)) {
            badgeOwned.add(badgeId);
            return true;
        }
        return false;
    }

    public void addPointScaleProgress(Long pointScaleId, Integer progress) {
        Integer currentProgress = pointScaleProgress.get(pointScaleId);
        currentProgress = (currentProgress == null) ? 0 : currentProgress;
        pointScaleProgress.put(pointScaleId, currentProgress + progress);
    }
}
