package server;

import dataaccess.*;
import Handler.ClearHandler;
import Handler.GameHandler;
import Handler.UserHandler;
import Service.ClearService;
import Service.GameService;
import Service.UserService;
import io.javalin.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();
    GameDAO gameDAO = new MemoryGameDAO();

    UserService userService = new UserService(userDAO, authDAO);
    GameService gameService = new GameService(gameDAO, authDAO);
    ClearService clearService = new ClearService(userDAO, authDAO, gameDAO);

    UserHandler userHandler = new UserHandler(userService);
    GameHandler gameHandler = new GameHandler(gameService);
    ClearHandler clearHandler = new ClearHandler(clearService);

    javalin.delete("/db",clearHandler::clear);
    javalin.post("/user",userHandler::register);
    javalin.post("/session",userHandler::login);
    javalin.delete("/session",userHandler::logout);
    javalin.get("/game",gameHandler::listGames);
    javalin.post("/game",gameHandler::createGame);
    javalin.put("/game",gameHandler::joinGame);
}

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
