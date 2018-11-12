package ch.heigvd.amt.mvc.services;

import ch.heigvd.amt.mvc.model.Beer;
import java.util.List;
import javax.ejb.Local;

/**
 * @see BeersDataStore
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
@Local
public interface BeersDataStoreLocal {

  /**
   *
   * @return
   */
  List<Beer> getAllBeers();

}
