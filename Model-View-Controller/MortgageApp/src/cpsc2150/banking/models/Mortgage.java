package cpsc2150.banking.models;

public class Mortgage extends AbsMortgage implements IMortgage {

    private int numPayments;
    private int numYears;
    private double percentDown;
    private double payment;
    private double rate;
    private double debtToIncomeRatio;
    private double principal;
    private double downPay;
    private double costOfH;
    private ICustomer c;


    public Mortgage (double costOfHome, double downPayment, int years, ICustomer customer)
    {
        numYears = years;
        costOfH = costOfHome;
        downPay = downPayment;
        c = customer;
        principal = costOfHome - downPayment;
        debtToIncomeRatio = (c.getMonthlyDebtPayments() * 12) / c.getIncome();
        percentDown = (downPayment / costOfHome)*100;
        rate = getRate();
        payment = ((rate * principal) / (1-(1+rate)-numPayments)); //
        numPayments = years * 12;
    }
    /**
     * This method checks to see if the mortgage loan has been approved or not.
     *
     * @return true if the loan is approved, false otherwise.
     *
     * @post loanApproved iff (Rate*12 < RATE_TOO_HIGH AND PercentDown >= MIN_PERCENT_DOWN AND DebtToIncomeRatio <= DTOITOOHIGH) AND
     *          Payment = #Payment AND Rate = #Rate AND Customer = #Customer AND DebtToIncomeRatio = #DebtToIncomeRatio AND
     *          Principal = #Principal AND NumberOfPayments = #NumberOfPayments AND PercentDown = #PercentDown
     */
    public boolean loanApproved(){
        if (rate >= 0.10) {
            return false;
        }
        if (downPay < 0.035*costOfH) {
            return false;
        }
        return ((c.getMonthlyDebtPayments() + payment) / (c.getIncome() / 12)) < 0.4;
    }

    /**
     * This method returns the amount that must be paid per month for the mortgage loan.
     *
     * @return the monthly payment on the loan
     *
     * @post getPayment = Payment AND
     *          Payment = #Payment AND Rate = #Rate AND Customer = #Customer AND DebtToIncomeRatio = #DebtToIncomeRatio AND
     *          Principal = #Principal AND NumberOfPayments = #NumberOfPayments AND PercentDown = #PercentDown
     */
    public double getPayment() {
        double monthRate = rate/12;
        double principalBalance = costOfH - downPay;
        double numPayments = numYears*12;

        payment = (monthRate*principalBalance)/(1-Math.pow((1+monthRate),(-1*numPayments)));
        return payment;
    }

    /**
     * This method returns the interest rate (APR).
     *
     * @return the interest rate (APR) for this customer
     *
     * @post getRate = Rate * 12 AND
     *          Payment = #Payment AND Rate = #Rate AND Customer = #Customer AND DebtToIncomeRatio = #DebtToIncomeRatio AND
     *          Principal = #Principal AND NumberOfPayments = #NumberOfPayments AND PercentDown = #PercentDown
     */
    public double getRate() {
        rate = BASERATE;

        if (numYears == 30) {
            rate += 0.01;
        } else if (numYears < 30) {
            rate += 0.005;
        }
        if (c.getCreditScore() < 500) {
            rate += 0.1;
        }
        if (c.getCreditScore() >= 500 && c.getCreditScore() <= 599) {
            rate += 0.05;
        }
        if (c.getCreditScore() >= 600 && c.getCreditScore() <= 699) {
            rate += 0.01;
        }
        if (c.getCreditScore() >= 700 && c.getCreditScore() <= 749) {
            rate += 0.005;
        }
        if (downPay < 0.2 * costOfH) {
            rate += 0.005;
        }
        return rate;
    }

    /**
     * This method returns the customer's principal amount for the mortgage loan.
     *
     * @return the principal amount of the loan
     *
     * @post getPrincipal = Principal AND
     *          Payment = #Payment AND Rate = #Rate AND Customer = #Customer AND DebtToIncomeRatio = #DebtToIncomeRatio AND
     *          Principal = #Principal AND NumberOfPayments = #NumberOfPayments AND PercentDown = #PercentDown
     */
    public double getPrincipal() {
        return principal;
        }

    /**
     * This method returns the amount of years the customer applied the mortgage loan for.
     *
     * @return the number of years the loan is for
     *
     * @post getYears = Years AND
     *          Payment = #Payment AND Rate = #Rate AND Customer = #Customer AND DebtToIncomeRatio = #DebtToIncomeRatio AND
     *          Principal = #Principal AND NumberOfPayments = #NumberOfPayments AND PercentDown = #PercentDown
     */
    public int getYears() {
        return numPayments / 12;
        }
}
