package creditcard.strategy.minimumPaymentStrategy;


import accountparty.account.Account;

import java.math.BigDecimal;

public class SilverMinimumPaymentStrategy implements MinimumPaymentStrategy{
    private static final BigDecimal RATE = BigDecimal.valueOf(0.1);
    @Override
    public BigDecimal minimumPayment(Account account) {
        BigDecimal mp = new BigDecimal("0.12");
        return account.getBalance().add(account.getBalance().multiply(mp));
    }
    public BigDecimal getRate() {
        return RATE;
    }
}
