package cr.ac.una.clinicauna.services;

import cr.ac.una.clinicauna.model.SlotsDto;
import cr.ac.una.clinicauna.util.Request;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;

public class SlotsService {
    public ResponseWrapper getSlots() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
    }

    public ResponseWrapper createSlot(SlotsDto slotDto) {
        try {
            Request request = new Request("SlotsController/create");
            request.post(slotDto);
            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                        ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: "
                        + request.getError(),
                        null);
            }
            slotDto = (SlotsDto) request.readEntity(SlotsDto.class);
            return new ResponseWrapper(ResponseCode.OK.getCode(), ResponseCode.OK, "Slot created successfully: ",
                    slotDto);
        } catch (Exception ex) {
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR, "Error in the service: " + ex.toString(),
                    null);
        }
    }

    public ResponseWrapper updateSlot() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
    }

    public ResponseWrapper deleteSlot() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
    }

    public ResponseWrapper getSlotById() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
    }
}
