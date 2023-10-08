package cr.ac.una.clinicaunaws.services;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import static cr.ac.una.clinicaunaws.util.PersistenceContext.PERSISTENCE_UNIT_NAME;
import java.util.ArrayList;
import java.util.List;
import cr.ac.una.clinicaunaws.dto.ReportValuesDto;
import cr.ac.una.clinicaunaws.entities.ReportValues;
import cr.ac.una.clinicaunaws.util.ResponseCode;
import cr.ac.una.clinicaunaws.util.ResponseWrapper;

/**
 * FIXME: Test it
 * 
 * @author arayaroma
 */
@Stateless
@LocalBean
public class ReportValuesService {

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    /**
     * Create a new ReportValues
     * 
     * @param reportValuesDto to be created
     * @return ResponseWrapper with the created ReportValues
     */
    public ResponseWrapper createReportValues(ReportValuesDto reportValuesDto) {
        try {
            ReportValues reportValues = new ReportValues(reportValuesDto);
            em.persist(reportValues);
            em.flush();
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "ReportValues created.",
                    reportValuesDto.convertFromEntityToDTO(reportValues,
                            reportValuesDto));
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not create the ReportValues.",
                    e.getMessage());
        }
    }

    /**
     * get a ReportValues by id
     * 
     * @param id of the ReportValues to be retrieved
     * @return ResponseWrapper with the retrieved ReportValues
     */
    public ResponseWrapper getReportValuesById(Long id) {
        try {
            ReportValues reportValues = em.find(ReportValues.class, id);
            if (reportValues != null) {
                ReportValuesDto reportValuesDto = new ReportValuesDto(reportValues);
                return new ResponseWrapper(
                        ResponseCode.OK.getCode(),
                        ResponseCode.OK,
                        "ReportValues retrieved.",
                        reportValuesDto.convertFromEntityToDTO(reportValues,
                                reportValuesDto));
            } else {
                return new ResponseWrapper(
                        ResponseCode.NOT_FOUND.getCode(),
                        ResponseCode.NOT_FOUND,
                        "ReportValues not found.",
                        "ReportValues with id " + id + " not found in the database.");
            }
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not retrieve the ReportValues.",
                    e.getMessage());
        }
    }

    /**
     * get all ReportValues
     * 
     * @return ResponseWrapper with all the ReportValues
     */
    @SuppressWarnings("unchecked")
    public ResponseWrapper getAllReportValues() {
        try {
            Query query = em.createNamedQuery("ReportValues.findAll", ReportValues.class);
            List<ReportValues> reportValuesList = (List<ReportValues>) query.getResultList();
            List<ReportValuesDto> reportValuesDtoList = new ArrayList<>();

            for (ReportValues reportValues : reportValuesList) {
                ReportValuesDto reportValuesDto = new ReportValuesDto(reportValues);
                reportValuesDtoList.add(reportValuesDto.convertFromEntityToDTO(reportValues,
                        reportValuesDto));
            }
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "ReportValues retrieved.",
                    reportValuesDtoList);
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not retrieve the ReportValues.",
                    e.getMessage());
        }
    }

    /**
     * Update a ReportValues
     * 
     * @param reportValuesDto to be updated
     * @return ResponseWrapper with the updated ReportValues
     */
    public ResponseWrapper updateReportValues(ReportValuesDto reportValuesDto) {
        try {
            ReportValues reportValues = em.find(ReportValues.class, reportValuesDto.getId());
            if (reportValues != null) {
                reportValues.updateReport(reportValuesDto);
                em.merge(reportValues);
                em.flush();
                return new ResponseWrapper(
                        ResponseCode.OK.getCode(),
                        ResponseCode.OK,
                        "ReportValues updated.",
                        reportValuesDto.convertFromEntityToDTO(reportValues,
                                reportValuesDto));
            } else {
                return new ResponseWrapper(
                        ResponseCode.NOT_FOUND.getCode(),
                        ResponseCode.NOT_FOUND,
                        "ReportValues not found.",
                        "ReportValues with id " + reportValuesDto.getId() + " not found in the database.");
            }
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not update the ReportValues.",
                    e.getMessage());
        }
    }

    /**
     * Delete a ReportValues by id
     * 
     * @param id of the ReportValues to be deleted
     * @return ResponseWrapper informing if the ReportValues was deleted
     */
    public ResponseWrapper deleteReportValues(Long id) {
        try {
            ReportValues reportValues = em.find(ReportValues.class, id);
            if (reportValues != null) {
                em.remove(reportValues);
                return new ResponseWrapper(
                        ResponseCode.OK.getCode(),
                        ResponseCode.OK,
                        "ReportValues deleted.",
                        null);
            } else {
                return new ResponseWrapper(
                        ResponseCode.NOT_FOUND.getCode(),
                        ResponseCode.NOT_FOUND,
                        "ReportValues not found.",
                        "ReportValues with id " + id + " not found in the database.");
            }
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "ReportValues could not be deleted.",
                    e.getMessage());
        }
    }

}
