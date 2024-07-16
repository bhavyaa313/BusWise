package buswise.helper;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenGenerator {

    public String GenerateToken( ) {


        String token = UUID.randomUUID().toString();
        return token;
    }


}
