package cr.ac.una.clinicauna.util;

/**
 * 
 * @author arayaroma
 */
public enum ClassType {
    AGENDA("TBL_AGENDA"),
    DOCTOR("TBL_DOCTOR"),
    GENERAL_INFORMATION("TBL_GENERAL_INFORMATION"),
    MEDICAL_APPOINTMENT("TBL_MEDICAL_APPOINTMENT"),
    MEDICAL_EXAM("TBL_MEDICAL_EXAM"),
    PATIENT("TBL_PATIENT"),
    PATIENT_CARE("TBL_PATIENT_CARE"),
    PATIENT_FAMILY_HISTORY("TBL_PATIENT_FAMILY_HISTORY"),
    PATIENT_PERSONAL_HISTORY("TBL_PATIENT_PERSONAL_HISTORY"),
    REPORT("TBL_REPORT"),
    REPORT_RECIPIENTS("TBL_REPORT_RECIPIENTS"),
    USER("TBL_USER");

    private String tableName;

    ClassType(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
