package cr.ac.una.clinicaunaws.services;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import static cr.ac.una.clinicaunaws.util.PersistenceContext.PERSISTENCE_UNIT_NAME;
import java.util.ArrayList;
import java.util.List;
import cr.ac.una.clinicaunaws.dto.PatientPersonalHistoryDto;
import cr.ac.una.clinicaunaws.entities.PatientPersonalHistory;
import cr.ac.una.clinicaunaws.util.ResponseCode;
import cr.ac.una.clinicaunaws.util.ResponseWrapper;

/**
 * FIXME: Test it
 * 
 * @author arayaroma
 */
@Stateless
@LocalBean
public class PatientPersonalHistoryService {

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    /**
     * Create a new PatientPersonalHistory
     * 
     * @param patientPersonalHistoryDto to be created
     * @return ResponseWrapper with the created PatientPersonalHistory
     */
    public ResponseWrapper createPatientPersonalHistory(PatientPersonalHistoryDto patientPersonalHistoryDto) {
        try {
            PatientPersonalHistory patientPersonalHistory = new PatientPersonalHistory(patientPersonalHistoryDto);
            em.persist(patientPersonalHistory);
            em.flush();
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "PatientPersonalHistory created.",
                    patientPersonalHistoryDto.convertFromEntityToDTO(patientPersonalHistory,
                            patientPersonalHistoryDto));
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not create the PatientPersonalHistory.",
                    e.getMessage());
        }
    }

    /**
     * get a PatientPersonalHistory by id
     * 
     * @param id of the PatientPersonalHistory to be retrieved
     * @return ResponseWrapper with the retrieved PatientPersonalHistory
     */
    public ResponseWrapper getPatientPersonalHistoryById(Long id) {
        try {
            PatientPersonalHistory patientPersonalHistory = em.find(PatientPersonalHistory.class, id);
            if (patientPersonalHistory == null) {
                return new ResponseWrapper(
                        ResponseCode.NOT_FOUND.getCode(),
                        ResponseCode.NOT_FOUND,
                        "PatientPersonalHistory not found.",
                        null);
            }
            PatientPersonalHistoryDto patientPersonalHistoryDto = new PatientPersonalHistoryDto();
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "PatientPersonalHistory retrieved.",
                    patientPersonalHistoryDto.convertFromEntityToDTO(patientPersonalHistory,
                            patientPersonalHistoryDto));
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not retrieve the PatientPersonalHistory.",
                    e.getMessage());
        }
    }

    /**
     * get all PatientPersonalHistory
     * 
     * @return ResponseWrapper with the retrieved PatientPersonalHistory
     */
    @SuppressWarnings("unchecked")
    public ResponseWrapper getAllPatientPersonalHistory() {
        try {
            Query query = em.createNamedQuery("PatientPersonalHistory.findAll", PatientPersonalHistory.class);
            List<PatientPersonalHistory> patientPersonalHistoryList = (List<PatientPersonalHistory>) query
                    .getResultList();
            List<PatientPersonalHistoryDto> patientPersonalHistoryDtoList = new ArrayList<>();

            for (PatientPersonalHistory patientPersonalHistory : patientPersonalHistoryList) {
                PatientPersonalHistoryDto patientPersonalHistoryDto = new PatientPersonalHistoryDto();
                patientPersonalHistoryDtoList.add(patientPersonalHistoryDto
                        .convertFromEntityToDTO(patientPersonalHistory, patientPersonalHistoryDto));
            }

            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "PatientPersonalHistory retrieved.",
                    patientPersonalHistoryDtoList);
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not retrieve the PatientPersonalHistory.",
                    e.getMessage());
        }
    }

    /**
     * update a PatientPersonalHistory
     * 
     * @param patientPersonalHistoryDto to be updated
     * @return ResponseWrapper with the updated PatientPersonalHistory
     */
    public ResponseWrapper updatePatientPersonalHistory(PatientPersonalHistoryDto patientPersonalHistoryDto) {
        try {
            PatientPersonalHistory patientPersonalHistory = em.find(PatientPersonalHistory.class,
                    patientPersonalHistoryDto.getId());
            if (patientPersonalHistory == null) {
                return new ResponseWrapper(
                        ResponseCode.NOT_FOUND.getCode(),
                        ResponseCode.NOT_FOUND,
                        "PatientPersonalHistory not found.",
                        null);
            }
            patientPersonalHistory = new PatientPersonalHistory(patientPersonalHistoryDto);
            em.merge(patientPersonalHistory);
            em.flush();
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "PatientPersonalHistory updated.",
                    patientPersonalHistoryDto.convertFromEntityToDTO(patientPersonalHistory,
                            patientPersonalHistoryDto));
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not update the PatientPersonalHistory.",
                    e.getMessage());
        }
    }

    /**
     * delete a PatientPersonalHistory by id
     * 
     * @param id to be deleted
     * @return ResponseWrapper with the deleted PatientPersonalHistory
     */
    public ResponseWrapper deletePatientPersonalHistory(Long id) {
        try {
            PatientPersonalHistory patientPersonalHistory = em.find(PatientPersonalHistory.class, id);
            if (patientPersonalHistory == null) {
                return new ResponseWrapper(
                        ResponseCode.NOT_FOUND.getCode(),
                        ResponseCode.NOT_FOUND,
                        "PatientPersonalHistory not found.",
                        null);
            }
            em.remove(patientPersonalHistory);
            em.flush();
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "PatientPersonalHistory deleted.",
                    null);
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not delete the PatientPersonalHistory.",
                    e.getMessage());
        }
    }

}