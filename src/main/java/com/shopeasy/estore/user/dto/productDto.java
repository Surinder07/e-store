package com.shopeasy.estore.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class productDto {
    public String productName;
    public double price;
    public int availableQuantity;
}
