package co.com.bancolombia.model.user.gateways;

import co.com.bancolombia.model.user.User;
import reactor.core.publisher.Mono;
public interface UserRepository {
    Mono<User> saveUser(User user);
    Mono<Boolean> existsUserByEmail(String email);
}
