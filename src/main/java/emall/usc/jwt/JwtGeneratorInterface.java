package emall.usc.jwt;

import java.util.Map;

import emall.usc.beans.User;

public interface JwtGeneratorInterface {
    Map<String, String> generateToken(User user);
}
