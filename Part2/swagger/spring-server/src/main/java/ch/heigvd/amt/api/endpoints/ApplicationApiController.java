package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.ApplicationsApi;
import ch.heigvd.amt.api.model.Application;
import ch.heigvd.amt.api.model.ApplicationWithoutId;
import ch.heigvd.amt.api.util.ApiResponseBuilder;
import ch.heigvd.amt.entities.*;
import ch.heigvd.amt.repositories.*;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.SecureRandom;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class ApplicationApiController implements ApplicationsApi {

    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    BadgeRepository badgeRepository;
    @Autowired
    PointScaleRepository pointScaleRepository;
    @Autowired
    RuleRepository ruleRepository;
    @Autowired
    ForeignUserRepository foreignUserRepository;


    @Override
    public ResponseEntity<Application> createApplication(@ApiParam(value = "", required = true) @RequestBody ApplicationWithoutId application) {

        if (application.getOwner() == null) {
            return ApiResponseBuilder.badRequestMessage("The 'owner' field is mandatory");
        }

        ApplicationEntity appEntity = toApplicationEntity(application);

        applicationRepository.save(appEntity);
        Long id = appEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(appEntity.getId())
                .toUri();

        return ResponseEntity.created(location).body(toApplication(appEntity));
    }

    @Override
    public ResponseEntity<Void> deleteAppById(@ApiParam(value = "", required = true) @PathVariable("appId") Long appId) {
        // This will safe delete everything this app owns
        ApplicationEntity applicationEntity = applicationRepository.findOne(appId);
        if (applicationEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (BadgeEntity b : badgeRepository.findAll()) {
            if (b.getApplicationId().equals(appId)) {
                badgeRepository.delete(b.getId());
            }
        }
        for (PointScaleEntity ps : pointScaleRepository.findAll()) {
            if (ps.getApplicationId().equals(appId)) {
                pointScaleRepository.delete(ps.getId());
            }
        }
        for (RuleEntity r : ruleRepository.findAll()) {
            if (r.getApplicationId().equals(appId)) {
                ruleRepository.delete(r.getId());
            }
        }
        for (ForeignUserEntity fu : foreignUserRepository.findAll()) {
            if (fu.getApplicationId().equals(appId)) {
                foreignUserRepository.delete(fu.getId());
            }
        }
        applicationRepository.delete(appId);
        return ResponseEntity.noContent().build();
    }

    private ApplicationEntity toApplicationEntity(ApplicationWithoutId application) {
        ApplicationEntity entity = new ApplicationEntity();
        entity.setOwner(application.getOwner());
        entity.setApiKey(generateKey());
        return entity;
    }

    private Application toApplication(ApplicationEntity entity) {
        Application app = new Application();
        app.setOwner(entity.getOwner());
        app.setApiKey(entity.getApiKey());
        app.setId(entity.getId());
        return app;
    }

    private String generateKey() {
        int keyLength = 30;
        String DICT = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < keyLength; i++) {
            int n = random.nextInt(DICT.length() - 1);
            sb.append(DICT.charAt(n));
        }
        return sb.toString();
    }
}
