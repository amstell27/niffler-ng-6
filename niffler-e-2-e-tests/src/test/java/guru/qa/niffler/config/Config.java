package guru.qa.niffler.config;

public interface Config {

    static Config getInstance() {
        return LocalConfig.INSTANCE;
    }

    String frontUrl();

    String spendUrl();
    String spendJdbcUrl();

    String ghUrl();

    String authUrl();
    String authJdbcUrl();

    String gatewayUrl();

    String userDataUrl();
    String userDataJdbcUrl();

    String currencyUrl();
    String currencyJdbcUrl();
}
