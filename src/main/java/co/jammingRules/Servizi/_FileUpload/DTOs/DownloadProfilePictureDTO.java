package co.jammingRules.Servizi._FileUpload.DTOs;

import co.jammingRules.Servizi._FileUpload.entities.UserEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DownloadProfilePictureDTO {
    private UserEntity userEntity;
    private byte[] profileImage;
}