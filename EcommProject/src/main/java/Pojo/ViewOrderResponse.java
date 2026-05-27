package Pojo;

public class ViewOrderResponse {

    private ViewOrderData data;
    private String message;

    public ViewOrderData getData() {
        return data;
    }

    public void setData(ViewOrderData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
