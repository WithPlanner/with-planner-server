package withplanner.global.config;

public class Constants {
    /**
     * 권한제외 대상
     *
     * @see SecurityConfig
     */
    public static final String[] permitAllArray = new String[]{
            "/",
            "/sign_up/**",
            "/login/**"
    };
}
