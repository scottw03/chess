package Handler;

import io.javalin.http.Context;
import Service.ClearService;

public class ClearHandler {
    private final ClearService service;
    public ClearHandler(ClearService service) {
        this.service = service;
    }
    public void clear(Context ctx) {
        try {
            service.clear();
            ctx.status(200);
            ctx.result("{}");
        } catch (Exception ex) {
            ctx.status(500);
            ctx.result("""
                    {"message":"Error: %s"}
                    """.formatted(ex.getMessage()));
        }
    }
}
