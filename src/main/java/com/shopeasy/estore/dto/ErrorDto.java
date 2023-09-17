package com.shopeasy.estore.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Builder
@Data

@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    //@ApiModelProperty(example = "400")
    private int statusCode;
    //@ApiModelProperty(example = "Customer Not Found")
    private String message;
    private Instant timestamp;
    //@ApiModelProperty(example = "Ap123")
    private String applicationId;
}
