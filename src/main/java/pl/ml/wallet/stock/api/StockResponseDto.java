package pl.ml.wallet.stock.api;
import lombok.Data;

import java.util.List;

@Data
public class StockResponseDto {
    private List<StockDto> data;

}
