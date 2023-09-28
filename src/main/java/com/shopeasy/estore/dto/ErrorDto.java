package com.shopeasy.estore.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ErrorDto {
    //this comment is only for testing pull request
    //@ApiModelProperty(example = "400")
    private int statusCode;
    //@ApiModelProperty(example = "Customer Not Found")
    private String message;
    private Instant timestamp;
    //@ApiModelProperty(example = "Ap123")
    private String applicationId;

    private String details;

}



