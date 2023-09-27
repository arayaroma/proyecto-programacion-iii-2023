package cr.ac.una.clinicaunaws.services;

import cr.ac.una.clinicaunaws.util.ResponseWrapper;
import jakarta.ejb.Local;

/**
 * 
 * @author arayaroma
 */
@Local
public interface UserService {
    ResponseWrapper ping();
}
