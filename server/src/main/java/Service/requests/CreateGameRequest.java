package Service.requests;

public record CreateGameRequest(
        String gameName, String authToken
) {
}
