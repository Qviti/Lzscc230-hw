package ge.homework.service;

import ge.homework.mapper.UserMapper;
import ge.homework.model.entity.Session;
import ge.homework.model.entity.User;
import ge.homework.model.enums.UserType;
import ge.homework.model.request.RegisterUserReq;
import ge.homework.model.request.VerifyUserReq;
import ge.homework.model.response.RegisterUserResp;
import ge.homework.repository.SessionRepository;
import ge.homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;
    private final Random random = new Random();

    public RegisterUserResp registerUser(RegisterUserReq req) {
        User user = User.builder()
                .username(req.getUsername())
                .fullName(req.getFullName())
                .password(req.getPassword())
                .userType(UserType.DEFAULT)
                .otp(random.nextInt(10000))
                .build();
        user = userRepository.save(user);
        return userMapper.toRegisterUserResp(user);
    }

    public RegisterUserResp verifyUser(VerifyUserReq req, long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getOtp() == req.getCode()) {
            user.setVerified(true);
            user.setOtp(null);
            user = userRepository.save(user);
        }
        return userMapper.toRegisterUserResp(user);
    }

    public boolean isUserAuthorized(String token) {
        return sessionRepository.findById(token)
                .map(Session::getUser)
                .map(User::isVerified)
                .orElse(false);
    }

    public User getUserByToken(String token) {
        return sessionRepository.findById(token)
                .map(Session::getUser)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
    }
}

