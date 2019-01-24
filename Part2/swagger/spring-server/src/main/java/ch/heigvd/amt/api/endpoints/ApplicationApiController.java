package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.ApplicationsApi;
import ch.heigvd.amt.api.model.Application;
import ch.heigvd.amt.api.model.ApplicationWithoutId;
import ch.heigvd.amt.api.util.ApiResponseBuilder;
import ch.heigvd.amt.entities.ApplicationEntity;
import ch.heigvd.amt.repositories.ApplicationRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.SecureRandom;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class ApplicationApiController implements ApplicationsApi {

    @Autowired
    ApplicationRepository applicationRepository;


    @Override
    public ResponseEntity<Application> createApplication(@ApiParam(value = "" ,required=true ) @RequestBody ApplicationWithoutId application) {

        if(application.getOwner() == null) {
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

    private ApplicationEntity toApplicationEntity(ApplicationWithoutId application) {
        ApplicationEntity entity = new ApplicationEntity();
        entity.setOwner(application.getOwner());
        entity.setApiKey(generateKey());
        return entity;
    }

    private Application toApplication(ApplicationEntity entity) {
        Application app = new Application();
        app.setOwner(entity.getOwner());
        return app;
    }

    private String generateKey() {
        int keyLength = 30;
        String DICT = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for(int i = 0; i < keyLength; i++) {
            int n = random.nextInt(DICT.length()-1);
            sb.append(DICT.charAt(n));
        }
        return sb.toString();
    }
}
