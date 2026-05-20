package Service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import chess.ChessGame;
import Service.requests.*;
import Service.results.*;

import java.util.Collection;

public class GameService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public GameService(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }
    public CreateGameResult createGame(CreateGameRequest request) throws Exception {
        AuthData auth = authDAO.getAuth(request.authToken());
        if (auth == null) {
            throw new Exception("unauthorized");
        }
        if (request.gameName() == null) {
            throw new Exception("bad request");
        }
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(
                        0,
                        null,
                        null,
                        request.gameName(),
                        game
                );
        int gameID = gameDAO.createGame(gameData);
        return new CreateGameResult(gameID);
    }
    public ListGamesResult listGames(
            ListGamesRequest request) throws Exception {
        AuthData auth = authDAO.getAuth(request.authToken());
        if (auth == null) {
            throw new Exception("unauthorized");
        }
        Collection<GameData> games = gameDAO.listGames();
        return new ListGamesResult(games);
    }
    public void joinGame(JoinGameRequest request) throws Exception {
        AuthData auth = authDAO.getAuth(request.authToken());
        if (auth == null) {
            throw new Exception("unauthorized");
        }
        GameData game = gameDAO.getGame(request.gameID());
        if (game == null) {
            throw new Exception("bad request");
        }
        String color = request.playerColor();
        if (color == null) {
            throw new Exception("bad request");
        }
        if (color.equals("WHITE")) {
            if (game.whiteUsername() != null) {
                throw new Exception("already taken");
            }
            game = new GameData(
                    game.gameID(),
                    auth.username(),
                    game.blackUsername(),
                    game.gameName(),
                    game.game()
            );
        }
        else if (color.equals("BLACK")) {
            if (game.blackUsername() != null) {
                throw new Exception("already taken");
            }
            game = new GameData(
                    game.gameID(),
                    game.whiteUsername(),
                    auth.username(),
                    game.gameName(),
                    game.game()
            );
        }
        else {
            throw new Exception("bad request");
        }
        gameDAO.updateGame(game);
    }
}