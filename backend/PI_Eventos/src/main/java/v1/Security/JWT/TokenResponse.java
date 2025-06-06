package v1.Security.JWT;

public class TokenResponse {
    String token;

    public TokenResponse() {
    }

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Implementación del patrón Builder
    public static class TokenResponseBuilder {
        private String token;

        public TokenResponseBuilder() {
        }

        public TokenResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public TokenResponse build() {
            return new TokenResponse(this.token);
        }
    }

    public static TokenResponseBuilder builder() {
        return new TokenResponseBuilder();
    }

}
