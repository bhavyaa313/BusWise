package buswise.dto;

public class MonthlySalesData {
    private int month;
    private double totalSales;
    private int numberOfBookings;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public int getNumberOfBookings() {
        return numberOfBookings;
    }

    public void setNumberOfBookings(int numberOfBookings) {
        this.numberOfBookings = numberOfBookings;
    }

    public MonthlySalesData(int month, double totalSales, int numberOfBookings) {
        this.month = month;
        this.totalSales = totalSales;
        this.numberOfBookings = numberOfBookings;
    }

    public MonthlySalesData() {
    }
}
