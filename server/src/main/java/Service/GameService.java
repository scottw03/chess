package Service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import chess.ChessGame;

import Service.requests.*;
import Service.results.*;

public class GameService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public GameService(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }
    public CreateGameResult createGame(
            CreateGameRequest request) throws Exception {
        AuthData auth = authDAO.getAuth(request.authToken());
        if (auth == null) {
            throw new Exception("unauthorized");
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
        return new ListGamesResult(
                gameDAO.listGames()
        );
    }
}
