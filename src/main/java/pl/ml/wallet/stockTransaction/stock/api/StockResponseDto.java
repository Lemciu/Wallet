package pl.ml.wallet.stockTransaction.stock.api;

import lombok.Data;

import java.util.List;

@Data
public class StockResponseDto {
    private List<StockDto> data;

}
