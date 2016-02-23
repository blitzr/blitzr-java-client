package com.blitzr.models.event;

import java.util.List;

public class EventProvider {
    private String currency;
    private float min_price;
    private String logo;
    private List<EventTicket> tickets;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getMin_price() {
        return min_price;
    }

    public void setMin_price(float min_price) {
        this.min_price = min_price;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<EventTicket> getTickets() {
        return tickets;
    }

    public void setTickets(List<EventTicket> tickets) {
        this.tickets = tickets;
    }
}
