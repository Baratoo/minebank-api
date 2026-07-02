package minebank_api.domain;

public enum TransactionType {
    TRANSFER("Transferência"),
    MARKET_BUY("Compra"),
    MARKET_SELL("Venda");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
