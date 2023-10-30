package cr.ac.una.clinicaunaws.util;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author arayaroma
 */
@Data
@NoArgsConstructor
public class QueryManager<D> {

    @SuppressWarnings("rawtypes")
    private static QueryManager instance;
    private String query;
    private String status;
    private List<D> result;

    @SuppressWarnings("rawtypes")
    public static QueryManager getInstance() {
        if (instance == null) {
            instance = new QueryManager();
        }
        return instance;
    }

}
