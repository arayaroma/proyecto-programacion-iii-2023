package cr.ac.una.clinicaunaws.util;

import java.util.List;
import lombok.Data;

/**
 * 
 * @author arayaroma
 */
@Data
public class QueryManager<D> {

    private String query;
    private String status;
    private List<D> result;

    @SuppressWarnings("unchecked")
    public void setResult(List<?> result) {
        this.result = (List<D>) result;
    }

}
