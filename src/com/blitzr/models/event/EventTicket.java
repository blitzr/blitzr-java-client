package com.blitzr.models.event;

import java.util.List;

public class EventTicket {
    private String name;
    private String url;
    private String currency;
    private String provider_id;
    private List<Float> price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public List<Float> getPrice() {
        return price;
    }

    public void setPrice(List<Float> price) {
        this.price = price;
    }

    public Float getMinPrice() {
        if (price.isEmpty()) {
            return null;
        }
        Float result = price.get(0);
        for (Float px : price) {
            if (px < result) {
                result = px;
            }
        }
        return result;
    }
}
