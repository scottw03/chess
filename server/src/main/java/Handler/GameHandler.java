package Handler;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.http.Context;
import Service.GameService;
import Service.requests.CreateGameRequest;
import Service.requests.JoinGameRequest;
import Service.requests.ListGamesRequest;
import Service.results.CreateGameResult;
import Service.results.ListGamesResult;

public class GameHandler {
    private final GameService service;
    private final Gson gson = new Gson();
    public GameHandler(GameService service) {
        this.service = service;
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        service = new GameService(gameDAO, authDAO);
    }
    public void createGame(Context ctx) {
        try {
            String authToken = ctx.header("authorization");
            CreateGameRequest body = gson.fromJson(ctx.body(), CreateGameRequest.class);
            CreateGameRequest request = new CreateGameRequest(body.gameName(), authToken);
            CreateGameResult result = service.createGame(request);
            ctx.status(200);
            ctx.result(gson.toJson(result));
        } catch (Exception ex) {
            handleException(ctx, ex);
        }
    }
    public void listGames(Context ctx) {
        try {
            String authToken = ctx.header("authorization");
            ListGamesRequest request = new ListGamesRequest(authToken);
            ListGamesResult result = service.listGames(request);
            ctx.status(200);
            ctx.result(gson.toJson(result));
        } catch (Exception ex) {
            handleException(ctx, ex);
        }
    }
    public void joinGame(Context ctx) {
        try {
            String authToken = ctx.header("authorization");
            JoinGameRequest body =
                    gson.fromJson(
                            ctx.body(),
                            JoinGameRequest.class
                    );
            JoinGameRequest request =
                    new JoinGameRequest(
                            body.playerColor(),
                            body.gameID(),
                            authToken
                    );
            service.joinGame(request);
            ctx.status(200);
            ctx.result("{}");

        } catch (Exception ex) {

            handleException(ctx, ex);
        }
    }
    private void handleException(Context ctx, Exception ex) {
        String message = ex.getMessage();
        switch (message) {
            case "bad request" -> ctx.status(400);
            case "unauthorized" -> ctx.status(401);
            case "already taken" -> ctx.status(403);
            default -> ctx.status(500);
        }
        ctx.result("""
                {"message":"Error: %s"}
                """.formatted(message));
    }
}
