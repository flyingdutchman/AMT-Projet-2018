package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.PointScalesApi;
import ch.heigvd.amt.api.model.PointScale;
import ch.heigvd.amt.api.model.PointScaleWithoutId;
import ch.heigvd.amt.api.util.ApiResponseBuilder;
import ch.heigvd.amt.entities.ApplicationEntity;
import ch.heigvd.amt.entities.PointScaleEntity;
import ch.heigvd.amt.repositories.ApplicationRepository;
import ch.heigvd.amt.repositories.PointScaleRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class PointScalesApiController implements PointScalesApi {

    @Autowired
    PointScaleRepository pointScaleRepository;
    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public ResponseEntity<PointScale> createPointScale(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey, @ApiParam(value = "", required = true) @RequestBody PointScaleWithoutId pointScale) {

        Long appId = getAppId(apiKey);
        if (appId == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }

        if (pointScale.getDescription() == null || pointScale.getName() == null) {
            return ApiResponseBuilder.badRequestMessage("The 'name' and 'description' fields are mandatory");
        }

        PointScaleEntity newPointScalEntity = toPointScaleEntity(pointScale, appId);
        PointScaleEntity foundDouble = null;

        for (PointScaleEntity pe : pointScaleRepository.findAll()) {
            if (pe.getApplicationId().equals(appId) && pe.getName().equals(newPointScalEntity.getName())) {
                foundDouble = pe;
                break;
            }
        }

        if (foundDouble != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(foundDouble.getId())
                    .toUri();
            return ApiResponseBuilder.conflictMessage(location);
        }

        pointScaleRepository.save(newPointScalEntity);
        Long id = newPointScalEntity.getId();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newPointScalEntity.getId())
                .toUri();

        return ResponseEntity.created(location).body(toPointScale(newPointScalEntity));
    }

    @Override
    public ResponseEntity<PointScale> getPointScaleById(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey, @ApiParam(value = "", required = true) @PathVariable("pointScaleId") Long pointScaleId) {
        Long appId = getAppId(apiKey);
        if (appId == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }
        PointScaleEntity pointScale = pointScaleRepository.findOne(pointScaleId);
        if (pointScale == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!pointScale.getApplicationId().equals(appId)) {
            return ApiResponseBuilder.forbiddenMessage();
        }
        return ResponseEntity.ok(toPointScale(pointScale));
    }

    @Override
    public ResponseEntity<List<PointScale>> getAllPointScales(@ApiParam(value = "The API key header", required = true) @RequestHeader(value = "apiKey", required = true) String apiKey) {
        Long appId = getAppId(apiKey);
        if (appId == null) {
            return ApiResponseBuilder.unauthorizedMessage();
        }
        List<PointScale> pointScales = new ArrayList<>();
        for (PointScaleEntity pointScaleEntity : pointScaleRepository.findAll()) {
            if (pointScaleEntity.getApplicationId().equals(appId)) {
                pointScales.add(toPointScale(pointScaleEntity));
            }
        }
        return ResponseEntity.ok(pointScales);
    }

    private PointScaleEntity toPointScaleEntity(PointScaleWithoutId pointScale, Long appId) {
        PointScaleEntity entity = new PointScaleEntity();
        entity.setName(pointScale.getName());
        entity.setDescription(pointScale.getDescription());
        entity.setApplicationId(appId);
        return entity;
    }

    private PointScale toPointScale(PointScaleEntity entity) {
        PointScale pointScale = new PointScale();
        pointScale.setId(entity.getId());
        pointScale.setName(entity.getName());
        pointScale.setDescription(entity.getDescription());
        pointScale.setAppId(entity.getApplicationId());
        return pointScale;
    }

    private Long getAppId(String apiKey) {
        if(apiKey == null)
            return null;
        return findKey(apiKey);
    }

    private Long findKey(String key) {
        for(ApplicationEntity ake : applicationRepository.findAll()) {
            if(ake.getApiKey().equals(key)) {
                return ake.getId();
            }
        }
        return null;
    }
}