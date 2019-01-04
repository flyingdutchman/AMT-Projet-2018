package io.avalia.badges.api.endpoints;

import io.avalia.badges.api.PointScaleApi;
import io.avalia.badges.api.model.PointScale;
import io.avalia.badges.entities.PointScaleEntity;
import io.avalia.badges.repositories.PointScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")


@Controller
public class PointScaleApiController implements PointScaleApi {

    @Autowired
    PointScaleRepository pointScaleRepository;

    @Override
    public ResponseEntity<Object> createPointScale(PointScale pointScale) {
        PointScaleEntity newPointScalEntity = toPointScaleEntity(pointScale);
        pointScaleRepository.save(newPointScalEntity);
        Long id = newPointScalEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newPointScalEntity.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<PointScale>> getPointScales() {

        List<PointScale> pointScales = new ArrayList<>();
        for(PointScaleEntity pointScaleEntity : pointScaleRepository.findAll()) {
            pointScales.add(toPointScale(pointScaleEntity));
        }

        return ResponseEntity.ok(pointScales);
    }

    private PointScaleEntity toPointScaleEntity(PointScale pointScale) {
        PointScaleEntity entity = new PointScaleEntity();
        entity.setName(pointScale.getName());
        entity.setValue(pointScale.getValue());
        return entity;
    }

    private PointScale toPointScale(PointScaleEntity entity) {
        PointScale pointScale = new PointScale();
        pointScale.setName(entity.getName());
        pointScale.setValue(entity.getValue());
        return pointScale;
    }
}
