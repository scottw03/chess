package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import service.requests.RegisterRequest;
import service.results.RegisterResult;

import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }
    public RegisterResult register(RegisterRequest request)
        throws Exception {
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new Exception("bad request");
        }
        if (userDAO.getUser(request.username()) != null) {
            throw new Exception("already taken");
        }
        UserData user = new UserData(
          request.username(), request.password(), request.email()
        );
        userDAO.createUser(user);
        String token = UUID.randomUUID().toString();
        AuthData auth = new AuthData(token, request.username());
        authDAO.createAuth(auth);
        return new RegisterResult(request.username(), token);
    }

}
