package com.example.amusetravelproejct.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ItemResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getAllItemId{
        private List<Long> item_ids;
    }
}
