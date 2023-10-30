package cr.ac.una.clinicauna.util;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author arayaroma
 */
public class QueryManager<D> {

    private String query;
    private HashMap<String, String> parameters = new HashMap<>();
    private String status;
    private List<D> result;

    public QueryManager() {
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public HashMap<String, String> getParameters() {
        return this.parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<D> getResult() {
        return this.result;
    }

    @SuppressWarnings("unchecked")
    public void setResult(List<?> result) {
        this.result = (List<D>) result;
    }

    @Override
    public String toString() {
        return "{" +
                " query='" + getQuery() + "'" +
                ", parameters='" + getParameters() + "'" +
                ", status='" + getStatus() + "'" +
                ", result='" + getResult() + "'" +
                "}";
    }

}
