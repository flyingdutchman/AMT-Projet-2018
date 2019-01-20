package ch.heigvd.amt.api.endpoints;

import ch.heigvd.amt.api.PointScalesApi;
import ch.heigvd.amt.api.model.PointScale;
import ch.heigvd.amt.api.model.PointScaleWithoutId;
import ch.heigvd.amt.entities.PointScaleEntity;
import ch.heigvd.amt.repositories.PointScaleRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class PointScalesApiController implements PointScalesApi {

    @Autowired
    PointScaleRepository pointScaleRepository;

    @Override
    public ResponseEntity<PointScale> createPointScale(@ApiParam(value = "", required = true) @RequestBody PointScaleWithoutId pointScale) {
        PointScaleEntity newPointScalEntity = toPointScaleEntity(pointScale);
        pointScaleRepository.save(newPointScalEntity);
        Long id = newPointScalEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newPointScalEntity.getId())
                .toUri();

        return ResponseEntity.created(location).body(toPointScale(newPointScalEntity));
    }

    @Override
    public ResponseEntity<PointScale> getPointScaleById(@ApiParam(value = "", required = true) @PathVariable("pointScaleId") Long pointScaleId) {
        PointScaleEntity pointScale = pointScaleRepository.findOne(pointScaleId);
        if (pointScale == null) {
            //TODO nicer
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(toPointScale(pointScale));
    }

    @Override
    public ResponseEntity<List<PointScale>> getAllPointScales() {

        List<PointScale> pointScales = new ArrayList<>();
        for (PointScaleEntity pointScaleEntity : pointScaleRepository.findAll()) {
            pointScales.add(toPointScale(pointScaleEntity));
        }

        return ResponseEntity.ok(pointScales);
    }

    private PointScaleEntity toPointScaleEntity(PointScaleWithoutId pointScale) {
        PointScaleEntity entity = new PointScaleEntity();
        //TODO Set id entity.setId()
        entity.setName(pointScale.getName());
        entity.setDescription(pointScale.getDescription());
        return entity;
    }

    private PointScale toPointScale(PointScaleEntity entity) {
        PointScale pointScale = new PointScale();
        pointScale.setId(entity.getId());
        pointScale.setName(entity.getName());
        pointScale.setDescription(entity.getDescription());
        return pointScale;
    }
}