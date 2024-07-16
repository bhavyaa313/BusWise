package buswise.dto;

public class TransactionHistoryDto {

    private String date;
    private int transactionId;
    private String description;
    private Double amount;
    private String debit;
    private Double balance;
    private Long count;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public TransactionHistoryDto(String date, int transactionId, String description, Double amount, String debit, Double balance, Long count) {
        this.date = date;
        this.transactionId = transactionId;
        this.description = description;
        this.amount = amount;
        this.debit = debit;
        this.balance = balance;
        this.count = count;
    }

    public TransactionHistoryDto() {
    }
}
