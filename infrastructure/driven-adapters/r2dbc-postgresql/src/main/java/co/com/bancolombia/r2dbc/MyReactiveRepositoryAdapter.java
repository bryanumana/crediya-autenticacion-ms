package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.r2dbc.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MyReactiveRepositoryAdapter implements UserRepository {
    private final MyReactiveRepository  myReactiveRepository;


    @Override
    public Mono<User> saveUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setLastName(user.getLastName());
        userEntity.setDateOfBirth(user.getDateOfBirth());
        userEntity.setAddress(user.getAddress());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setEmail(user.getEmail());
        userEntity.setBaseSalary(user.getBaseSalary());

        return myReactiveRepository.save(userEntity)
                .map(saved -> user);
    }

    @Override
    public Mono<Boolean> existsUserByEmail(String email) {
        return myReactiveRepository.existsByEmail(email);
    }
}
