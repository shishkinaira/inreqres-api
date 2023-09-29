package in.reqres.models;
import lombok.Data;

@Data
public class UserUpdatePatchResponseModel {
    String name;
    String job;
    String updatedAt;
}
