package cr.ac.una.clinicaunaws.services;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import static cr.ac.una.clinicaunaws.util.PersistenceContext.PERSISTENCE_UNIT_NAME;
import java.util.ArrayList;
import java.util.List;
import cr.ac.una.clinicaunaws.dto.SlotsDto;
import cr.ac.una.clinicaunaws.entities.Slots;
import cr.ac.una.clinicaunaws.util.ResponseCode;
import cr.ac.una.clinicaunaws.util.ResponseWrapper;

/**
 * FIXME: Test it
 * 
 * @author arayaroma
 */
@Stateless
@LocalBean
public class SlotsService {

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    /**
     * Create a new Slots
     * 
     * @param slotsDto to be created
     * @return ResponseWrapper with the created Slots
     */
    public ResponseWrapper createSlots(SlotsDto slotsDto) {
        try {
            Slots slots = new Slots(slotsDto);
            em.persist(slots);
            em.flush();
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "Slots created.",
                    slotsDto.convertFromEntityToDTO(slots,
                            slotsDto));
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not create the Slots.",
                    e.getMessage());
        }
    }

    /**
     * get a Slots by id
     * 
     * @param id of the Slots to be retrieved
     * @return ResponseWrapper with the retrieved Slots
     */
    public ResponseWrapper getSlotsById(Long id) {
        try {
            Slots slots = em.find(Slots.class, id);
            if (slots != null) {
                SlotsDto slotsDto = new SlotsDto(slots);
                return new ResponseWrapper(
                        ResponseCode.OK.getCode(),
                        ResponseCode.OK,
                        "Slots retrieved.",
                        slotsDto.convertFromEntityToDTO(slots,
                                slotsDto));
            } else {
                return new ResponseWrapper(
                        ResponseCode.NOT_FOUND.getCode(),
                        ResponseCode.NOT_FOUND,
                        "Slots not found.",
                        "Slots with id " + id + " not found in the database.");
            }
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not retrieve the Slots.",
                    e.getMessage());
        }
    }

    /**
     * get all Slots
     * 
     * @return ResponseWrapper with all Slots
     */
    @SuppressWarnings("unchecked")
    public ResponseWrapper getAllSlots() {
        try {
            Query query = em.createNamedQuery("Slots.findAll", Slots.class);
            List<Slots> slotsList = (List<Slots>) query.getResultList();
            List<SlotsDto> slotsDtoList = new ArrayList<>();

            for (Slots slots : slotsList) {
                SlotsDto slotsDto = new SlotsDto(slots);
                slotsDtoList.add(slotsDto.convertFromEntityToDTO(slots,
                        slotsDto));
            }
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "All Slots retrieved.",
                    slotsDtoList);
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not retrieve all Slots.",
                    e.getMessage());
        }
    }

    /**
     * Update a Slots
     * 
     * @param slotsDto to be updated
     * @return ResponseWrapper with the updated Slots
     */
    public ResponseWrapper updateSlots(SlotsDto slotsDto) {
        try {
            Slots slots = em.find(Slots.class, slotsDto.getId());
            if (slots != null) {
                slots.updateSlots(slotsDto);
                em.merge(slots);
                em.flush();
                return new ResponseWrapper(
                        ResponseCode.OK.getCode(),
                        ResponseCode.OK,
                        "Slots updated.",
                        slotsDto.convertFromEntityToDTO(slots,
                                slotsDto));
            } else {
                return new ResponseWrapper(
                        ResponseCode.NOT_FOUND.getCode(),
                        ResponseCode.NOT_FOUND,
                        "Slots not found.",
                        "Slots with id " + slotsDto.getId() + " not found in the database.");
            }
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Slots could not be updated.",
                    e.getMessage());
        }
    }

    /**
     * Delete a Slots by id
     * 
     * @param id of the Slots to be deleted
     * @return ResponseWrapper informing if the Slots was deleted successfully or
     *         not
     */
    public ResponseWrapper deleteSlots(Long id) {
        try {
            Slots slots = em.find(Slots.class, id);
            if (slots != null) {
                em.remove(slots);
                em.flush();
                return new ResponseWrapper(
                        ResponseCode.OK.getCode(),
                        ResponseCode.OK,
                        "Slots deleted.",
                        null);
            } else {
                return new ResponseWrapper(
                        ResponseCode.NOT_FOUND.getCode(),
                        ResponseCode.NOT_FOUND,
                        "Slots not found.",
                        "Slots with id " + id + " not found in the database.");
            }
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Slots could not be deleted.",
                    e.getMessage());
        }
    }

}
