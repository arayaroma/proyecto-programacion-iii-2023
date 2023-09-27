package cr.ac.una.clinicaunaws.util;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 
 * @author arayaroma
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "listWrapper")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListWrapper<D> {

    @XmlElement(name = "element")
    private List<D> list;
}
