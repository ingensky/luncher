package edu.sky.luncher.util;

import edu.sky.luncher.domain.Restaurant;
import edu.sky.luncher.domain.User;
import edu.sky.luncher.util.exception.IllegalRequestDataException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class Util {

    private Util() {
    }

    public static void checkAccess(Restaurant restaurant, User user) {
        if (!restaurant.getAdministrators().contains(user)) {
            throw new IllegalRequestDataException("Access denied");
        }
    }

    public static URI getUri(Long id, String restUrl) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(restUrl + "/{id}")
                .buildAndExpand(id).toUri();
    }
}
