package com.fx.tool.domain;


import java.util.List;

public class CoinMessage {
    private String coinName;
    private List<String> addressList;
    private String path;
    private Boolean needAddressValid;

    public CoinMessage() {
    }

    public String getCoinName() {
        return this.coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public List<String> getAddressList() {
        return this.addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getNeedAddressValid() {
        return this.needAddressValid;
    }

    public void setNeedAddressValid(Boolean needAddressValid) {
        this.needAddressValid = needAddressValid;
    }

    public String toString() {
        return "CoinMessage{coinName='" + this.coinName + '\'' + ", addressList=" + this.addressList + ", path='" + this.path + '\'' + ", needAddressValid=" + this.needAddressValid + '}';
    }
}
