package in.cm.model;


import lombok.Data;

@Data

public class CustomerVerification {
    private Integer verificationID;
    private String verificationDate;
    private String status;
    private String remarks;

    // Getters and Setters
}

