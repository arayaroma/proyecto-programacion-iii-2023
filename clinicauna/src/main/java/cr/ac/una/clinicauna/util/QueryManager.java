package cr.ac.una.clinicauna.util;

import java.util.List;
import cr.ac.una.clinicauna.model.UserDto;

/**
 * 
 * @author arayaroma
 */
public class QueryManager {

    private String query;
    private String status;
    private List<UserDto> result;

    public QueryManager() {
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UserDto> getResult() {
        return this.result;
    }

    public void setResult(List<UserDto> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "{" +
                " query='" + getQuery() + "'" +
                ", status='" + getStatus() + "'" +
                ", result='" + getResult() + "'" +
                "}";
    }

}
