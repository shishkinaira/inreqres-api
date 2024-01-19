package in.reqres.models;
import lombok.Data;

@Data
public class RegistrationResponseUnsuccessfulModel {
    String error;
    Integer status;
}
