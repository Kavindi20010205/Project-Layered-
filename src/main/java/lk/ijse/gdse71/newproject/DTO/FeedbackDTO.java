package lk.ijse.gdse71.newproject.DTO;
//import lombok.*;

import java.sql.Date;

public class FeedbackDTO {
    private String feedbackId;
    private String productId;
    private String clientId;
    private Date date;
    private String comment;
    private int rating;

    public FeedbackDTO(String feedbackId, String productId, String clientId, Date date, String comment, int rating) {
        this.feedbackId = feedbackId;
        this.productId = productId;
        this.clientId = clientId;
        this.date = date;
        this.comment = comment;
        this.rating = rating;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public FeedbackDTO() {

    }
}
