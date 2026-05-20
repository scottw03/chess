package handler;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.http.Context;
import service.UserService;
import service.requests.LoginRequest;
import service.requests.RegisterRequest;
import service.requests.LogoutRequest;
import service.results.LoginResult;
import service.results.RegisterResult;

public class UserHandler {
    private final UserService service;
    private final Gson gson = new Gson();
    public UserHandler(UserService service) {
        this.service = service;
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
    public void logout(Context ctx) {
        try {
            String authToken = ctx.header("authorization");
            LogoutRequest request = new LogoutRequest(authToken);
            service.logout(request);
            ctx.status(200);
            ctx.result("{}");
        } catch (Exception ex) {
            ctx.status(401);
            ctx.result("""
                    {"message":"Error: %s"}
                    """.formatted(ex.getMessage()));
        }
    }
}
