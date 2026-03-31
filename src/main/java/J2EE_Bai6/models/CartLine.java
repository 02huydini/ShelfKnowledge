package J2EE_Bai6.models;

import lombok.Data;

@Data
public class CartLine {
    private Book book;
    private int quantity;
    private long lineTotal;
}

