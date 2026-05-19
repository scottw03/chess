package Handlers;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.http.Context;
import Service.UserService;
import Service.requests.LoginRequest;
import Service.requests.RegisterRequest;
import Service.requests.LogoutRequest;
import Service.results.LoginResult;
import Service.results.RegisterResult;

public class UserHandler {
    private final UserService service;
    private final Gson gson = new Gson();
    public UserHandler() {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        service = new UserService(userDAO, authDAO);
    }
    public void register(Context ctx) {
        try {
            RegisterRequest request =
                    gson.fromJson(
                            ctx.body(),
                            RegisterRequest.class
                    );
            RegisterResult result = service.register(request);
            ctx.status(200);
            ctx.result(gson.toJson(result));
        } catch (Exception ex) {
            ctx.status(400);
            ctx.result("""
                    {"message":"Error: %s"}
                    """.formatted(ex.getMessage()));
        }
    }
    public void login(Context ctx) {
        try {
            LoginRequest request =
                    gson.fromJson(
                            ctx.body(),
                            LoginRequest.class
                    );
            LoginResult result = service.login(request);
            ctx.status(200);
            ctx.result(gson.toJson(result));
        } catch (Exception ex) {
            ctx.status(401);
            ctx.result("""
                    {"message":"Error: %s"}
                    """.formatted(ex.getMessage()));
        }
    }

}
