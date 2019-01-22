package ch.heigvd.amt.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
public class ForeignUserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String applicationUserId;
    private ArrayList<Long> badgeOwned;
    private HashMap<Long, Integer> badgeProgress;
    private HashMap<Long, Integer> pointScaleProgress;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(String applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    public ArrayList<Long> getBadgeOwned() {
        return badgeOwned;
    }

    public void setBadgeOwned(ArrayList<Long> badgeOwned) {
        this.badgeOwned = badgeOwned;
    }

    public HashMap<Long, Integer> getBadgeProgress() {
        return badgeProgress;
    }

    public void setBadgeProgress(HashMap<Long, Integer> badgeProgress) {
        this.badgeProgress = badgeProgress;
    }

    public HashMap<Long, Integer> getPointScaleProgress() {
        return pointScaleProgress;
    }

    public void setPointScaleProgress(HashMap<Long, Integer> pointScaleProgress) {
        this.pointScaleProgress = pointScaleProgress;
    }

    public void addOwnedBadge(Long badgeId) {
        badgeOwned.add(badgeId);
    }

    public void addPointScaleProgress(Long pointScaleId, Integer progress) {
        Integer currentProgress = pointScaleProgress.get(pointScaleId);
        currentProgress = (currentProgress == null) ? 0 : currentProgress;
        pointScaleProgress.put(pointScaleId, currentProgress + progress);
    }
}
