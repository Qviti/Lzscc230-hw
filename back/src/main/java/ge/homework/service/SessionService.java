package ge.homework.service;

import ge.homework.mapper.UserMapper;
import ge.homework.model.entity.Food;
import ge.homework.model.entity.Session;
import ge.homework.model.entity.User;
import ge.homework.model.enums.UserType;
import ge.homework.model.request.AddFoodReq;
import ge.homework.model.request.UserCredentialsReq;
import ge.homework.model.response.FoodResp;
import ge.homework.model.response.RegisterUserResp;
import ge.homework.repository.FoodRepository;
import ge.homework.repository.SessionRepository;
import ge.homework.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;
    private final FoodRepository foodRepository;

    public RegisterUserResp createSession(UserCredentialsReq req, HttpServletResponse response) {
        User user = userRepository.findByUsernameAndPassword(req.getUsername(), req.getPassword())
                .orElseThrow(() -> new RuntimeException("could not authorize"));

        String token = UUID.randomUUID().toString();

        Session session = Session.builder()
                .token(token)
                .user(user)
                .build();
        sessionRepository.save(session);

        response.addHeader("X-Session-Token", token);

        return userMapper.toRegisterUserResp(user);
    }

    public RegisterUserResp getSession(String token) {
        Session session = sessionRepository.findById(token)
                .orElseThrow(() -> new RuntimeException("no active session"));
        return userMapper.toRegisterUserResp(session.getUser());
    }

    public FoodResp addFood(AddFoodReq req, String token) {
        Session session = sessionRepository.findById(token)
                .orElseThrow(() -> new RuntimeException("no active session"));

        User user = session.getUser();

        if (user.getUserType() != UserType.ADMIN) {
            throw new RuntimeException("user is not admin!");
        }

        Food food = Food.builder()
                .category(req.getCategory())
                .name(req.getName())
                .price((int) req.getPrice())
                .build();
        foodRepository.save(food);
        return FoodResp.builder()
                .id(food.getId())
                .category(food.getCategory())
                .name(food.getName())
                .price(food.getPrice())
                .build();
    }
    
    public User getVerifiedUser(String token) {
        var session = sessionRepository.findById(token)
            .orElseThrow(() -> new RuntimeException("no active session"));

        User user = session.getUser();

        if(!user.isVerified()) {
            throw new RuntimeException("user not verified");
        }
        
        return user;
    }
    public User getAdminUser(String token) {
        var session = sessionRepository.findById(token)
            .orElseThrow(() -> new RuntimeException("no active session"));

        User user = session.getUser();

        if(user.getUserType() != UserType.ADMIN) {
            throw new RuntimeException("no admin permission");
        }
        
        return user;
    }
}


