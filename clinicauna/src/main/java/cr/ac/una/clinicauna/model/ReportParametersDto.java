package cr.ac.una.clinicauna.model;

/**
 * 
 * @author arayaroma
 */
public class ReportParametersDto {
    private Long id;
    private ReportDto report;
    private String name;
    private String value;
    private Long version;

    public ReportParametersDto() {
    }

    public ReportParametersDto(ReportParametersDto reportParametersDto) {
        this();
        setId(reportParametersDto.getId());
        setReport(reportParametersDto.getReport());
        setName(reportParametersDto.getName());
        setValue(reportParametersDto.getValue());
        setVersion(reportParametersDto.getVersion());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportDto getReport() {
        return this.report;
    }

    public void setReport(ReportDto report) {
        this.report = report;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", report='" + getReport() + "'" +
                ", name='" + getName() + "'" +
                ", value='" + getValue() + "'" +
                ", version='" + getVersion() + "'" +
                "}";
    }

}