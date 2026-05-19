package Service.requests;

public record JoinGameRequest(
        String playerColor, int gameID, String authToken
) {
}
