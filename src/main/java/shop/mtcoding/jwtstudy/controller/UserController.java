package shop.mtcoding.jwtstudy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.jwtstudy.config.auth.JwtProvider;
import shop.mtcoding.jwtstudy.model.User;
import shop.mtcoding.jwtstudy.model.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user") // 인증 필요
    public ResponseEntity<?> user() {
        return ResponseEntity.ok().body("접근 성공");
    }

    @GetMapping("/") // 인증 불필요
    public ResponseEntity<?> main() {
        return ResponseEntity.ok().body("접근 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(User user){
        Optional<User> userOp = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(userOp.isPresent()){ // 값이 있다면
            String jwt = JwtProvider.create(userOp.get());
            return  ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body("로그인 성공");
        }else {
            return  ResponseEntity.badRequest().build();
        }
    }
}
