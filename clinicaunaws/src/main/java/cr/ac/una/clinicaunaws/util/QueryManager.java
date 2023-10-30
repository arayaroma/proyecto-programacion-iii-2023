package cr.ac.una.clinicaunaws.util;

import java.util.HashMap;
import java.util.List;
import lombok.Data;

/**
 * 
 * @author arayaroma
 */
@Data
public class QueryManager<D> {

    private String query;
    private HashMap<String, String> parameters = new HashMap<>();
    private String status;
    private List<D> result;

    @SuppressWarnings("unchecked")
    public void setResult(List<?> result) {
        this.result = (List<D>) result;
    }

    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public void removeParameter(String key) {
        parameters.remove(key);
    }

    public void clearParameters() {
        parameters.clear();
    }

    public boolean containsKey(String key) {
        return parameters.containsKey(key);
    }

    public boolean containsValue(String value) {
        return parameters.containsValue(value);
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    public int size() {
        return parameters.size();
    }

}
