package com.gracyma.onlineshoppingproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemSummary {
    private String title;
    private String price;
    private String imageUrl;
}

