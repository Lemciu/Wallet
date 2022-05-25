package pl.ml.wallet.transaction;

import org.springframework.format.annotation.DateTimeFormat;
import pl.ml.wallet.stock.Stock;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @ManyToOne
    private Stock stock; // kupiliśmy BTC
    private BigDecimal amount; // 1 sztukę
    private BigDecimal buyPrice; // po 35k sztuka // 2k sztuka
    // te poniżej na razie wyłącze, niech wszystko na usd(t) się opiera i później ewentualnie przelicza
//    private BigDecimal buyCurrencyPrice; // chyba też to potrzebne będzie
//    @ManyToOne
//    private Stock buyCurrency; // zapłaciliśmy w $ // w ETH
    // postarać się dorzucić wykres?
    // jest co robić i w sumie to juz jest całkiem konkretne
    //dobra na razie osobno! tylko krypto płatności, później dodamy możliwość przelewania tej 'kasy' do innego 'portfela podgrupy'
    //albo tutaj po prostu dodać do jakiej categori to idzie/ w konstruktorze nie musi być wszystko wypełnione
    //na razie tak/ tylko jak połączyć to z wydatkami ?
    //i z jednego będziemy mogli przelać do dreugiego np z oszczednosci x tetheru na zlotówki i do oszczedności czy tam odwrotnie
//    private Category category; Account ?//wydatki,



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getBuyValue() {
        return amount.multiply(buyPrice);
    }

    public BigDecimal getCurrentValue() {
        return amount.multiply(stock.getCurrentPrice());
    }

//    public BigDecimal getBuyCurrencyPrice() {
//        return buyCurrencyPrice;
//    }
//
//    public void setBuyCurrencyPrice(BigDecimal buyCurrencyPrice) {
//        this.buyCurrencyPrice = buyCurrencyPrice;
//    }
//
//    public Stock getBuyCurrency() {
//        return buyCurrency;
//    }
//
//    public void setBuyCurrency(Stock buyCurrency) {
//        this.buyCurrency = buyCurrency;
//    }

}

