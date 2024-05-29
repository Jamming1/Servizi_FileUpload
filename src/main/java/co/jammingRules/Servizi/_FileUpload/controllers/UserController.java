package co.jammingRules.Servizi._FileUpload.controllers;

import co.jammingRules.Servizi._FileUpload.DTOs.UserRequestDTO;
import co.jammingRules.Servizi._FileUpload.DTOs.UserResponseDTO;
import co.jammingRules.Servizi._FileUpload.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO newUser = userService.createNewUser(userRequestDTO);
        if(newUser== null){
            return ResponseEntity.status(420).body("This user is null");
        }
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/getSingle/{user_id}")
    public ResponseEntity<?> getOne(@PathVariable Long userId){
        Optional<UserResponseDTO> user = userService.findUserById(userId);
        if (user.isEmpty()){
            return ResponseEntity.status(422).body("UserEntity not found for the id: "+ userId);
        }
        return ResponseEntity.ok().body(user);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<UserResponseDTO> users = userService.getAll();
        return ResponseEntity.ok().body(users);
    }
    @PutMapping("/update/{user_id}")
    public ResponseEntity<?> update(@PathVariable Long userId,@RequestBody UserRequestDTO body){
        Optional<UserResponseDTO> newUser = userService.updateById(userId, body);
        return ResponseEntity.ok().body(newUser);
    }
    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> delete(@PathVariable Long userId){
        userService.deleteById(userId);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/uploadPicture/{id}/profile")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable Long id, @RequestParam MultipartFile profilePicture) throws Exception {
        Optional<UserResponseDTO> result = userService.findUserById(id);
        if (result == null) {
            return ResponseEntity.status(422).body("User not found");
        } else {
            userService.uploadProfilePicture(id, profilePicture);
        }
        return ResponseEntity.ok(result);
    }

    @SneakyThrows
    @GetMapping("/getProfilePicture/{id}/profile")
    public @ResponseBody byte[] getProfilePicture(@PathVariable Long id, HttpServletResponse response) {
        byte[] downloadProfilePictureDTO = userService.downloadProfilePicture(id);
        String fileName = downloadProfilePictureDTO.toString();
        if (fileName == null) throw new Exception("User does not have a profile picture");
        String extention = FilenameUtils.getExtension(fileName);
        switch (extention) {
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\"");
        return downloadProfilePictureDTO.getClass().newInstance();
    }

}
