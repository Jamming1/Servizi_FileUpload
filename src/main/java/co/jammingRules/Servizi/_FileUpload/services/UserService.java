package co.jammingRules.Servizi._FileUpload.services;

import co.jammingRules.Servizi._FileUpload.DTOs.UserRequestDTO;
import co.jammingRules.Servizi._FileUpload.DTOs.UserResponseDTO;
import co.jammingRules.Servizi._FileUpload.entities.UserEntity;
import co.jammingRules.Servizi._FileUpload.models.UserModel;
import co.jammingRules.Servizi._FileUpload.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileRepositoryService fileRepositoryService;

    public UserResponseDTO createNewUser(UserRequestDTO user) {
        UserModel userModel = new UserModel(user.getName(), user.getSurname(), user.getEmail(), user.getProfilePicture());
        UserModel newUser = UserModel.entityToModel(userRepository.saveAndFlush(UserModel.modelToEntity(userModel)));
        return UserModel.modelToDto(newUser);
    }

    public Optional<UserResponseDTO> findUserById(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UserModel userModel = UserModel.entityToModel(user.get());
            return Optional.of(UserModel.modelToDto(userModel));
        }
        return Optional.empty();
    }

    public List<UserResponseDTO> getAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        if (userEntities.isEmpty()) {
            return null;
        } else {
            return userEntities.stream()
                    .map(UserModel::entityToModel)
                    .map(UserModel::modelToDto)
                    .toList();
        }
    }

    public Optional<UserResponseDTO> updateById(Long userId, UserRequestDTO user) {
        Optional<UserEntity> toUpdate = userRepository.findById(userId);
        if (toUpdate.isPresent()) {
            toUpdate.get().setName(user.getName() == null ? toUpdate.get().getName() : user.getName());
            toUpdate.get().setSurname(user.getSurname() == null ? toUpdate.get().getSurname() : user.getSurname());
            toUpdate.get().setSurname(user.getSurname() == null ? toUpdate.get().getSurname() : user.getSurname());
            toUpdate.get().setEmail(user.getEmail() == null ? toUpdate.get().getEmail() : user.getEmail());
            toUpdate.get().setProfilePicture(user.getProfilePicture() == null ? toUpdate.get().getProfilePicture() : user.getProfilePicture());
            UserEntity updatedUserEntityEntity = userRepository.saveAndFlush(toUpdate.get());
            UserModel updatedUser = UserModel.entityToModel(updatedUserEntityEntity);
            return Optional.of(UserModel.modelToDto(updatedUser));
        }
        return Optional.empty();
    }

    public Boolean deleteById(Long userId) {
        if (userId == null) {
            return false;
        } else {
            userRepository.deleteById(userId);
            return true;
        }
    }

    @SneakyThrows
    public void uploadProfilePicture(Long userId, MultipartFile pp) {
        Optional<UserEntity> result = userRepository.findById(userId);
        if (result.isPresent()) {
            if (!pp.isEmpty()) {
                fileRepositoryService.remove(String.valueOf(pp));
                String fileName = fileRepositoryService.upload(pp);
                result.get().setProfilePicture(fileName);
                UserEntity savedUser = userRepository.saveAndFlush(result.get());
                UserModel savedUserModel = UserModel.entityToModel(savedUser);
                UserModel.modelToDto(savedUserModel);
            }
        }
    }

    public byte[] downloadProfilePicture(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        return Objects.requireNonNull(user.map(UserEntity::getProfilePicture).orElse(null)).getBytes();
    }
}
