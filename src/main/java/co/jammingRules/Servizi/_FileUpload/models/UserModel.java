package co.jammingRules.Servizi._FileUpload.models;

import co.jammingRules.Servizi._FileUpload.DTOs.UserRequestDTO;
import co.jammingRules.Servizi._FileUpload.DTOs.UserResponseDTO;
import co.jammingRules.Servizi._FileUpload.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long userId;
    private String name;
    private String surname;
    private String email;
    private String profilePicture;

    public UserModel(String name,String surname, String email, String profilePicture) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.profilePicture = profilePicture;
    }

    public static UserResponseDTO modelToDto(UserModel userModel) {
        return new UserResponseDTO(userModel.getUserId(), userModel.getName(), userModel.getSurname(), userModel.getEmail(), userModel.getProfilePicture());
    }

    public static UserEntity modelToEntity(UserModel userModel) {
        return new UserEntity(userModel.getUserId(), userModel.getName(), userModel.getSurname(), userModel.getEmail(), userModel.getProfilePicture());
    }

    public static UserModel entityToModel(UserEntity userEntity) {
        return new UserModel(userEntity.getUserId(), userEntity.getName(), userEntity.getSurname(), userEntity.getEmail(), userEntity.getProfilePicture());
    }

    public static UserModel DTOtoModel(UserRequestDTO requestDTO) {
        return new UserModel(requestDTO.getName(), requestDTO.getSurname(), requestDTO.getEmail(), requestDTO.getProfilePicture());
    }
}
