package emall.usc.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import emall.usc.beans.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtGeneratorImpl implements JwtGeneratorInterface{
    @Value("${jwt.secret}")
    private String secret;
    @Value("${app.jwttoken.message}")
    private String message;
    @Override
    public Map<String, String> generateToken(User user) {
        String jwtToken="";
        jwtToken = Jwts.builder().setSubject(user.getUsername()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secret").compact();
        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token", jwtToken);
        jwtTokenGen.put("message", message);
        return jwtTokenGen;
    }
  }
