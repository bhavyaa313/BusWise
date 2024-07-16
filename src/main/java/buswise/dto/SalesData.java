package buswise.dto;

import buswise.model.Bookings;

import java.util.List;

public class SalesData {
    private double totalSales;
    private int numberOfBookings;


    public SalesData() {

    }



    public void setNumberOfBookings(int numberOfBookings) {
        this.numberOfBookings = numberOfBookings;
    }



    public double getTotalSales() {
        return totalSales;
    }

    public int getNumberOfBookings() {
        return numberOfBookings;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }
}
