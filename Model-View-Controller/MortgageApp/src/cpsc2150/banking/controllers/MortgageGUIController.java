package cpsc2150.banking.controllers;
import cpsc2150.banking.views.*;
import cpsc2150.banking.models.*;

public class MortgageGUIController implements IMortgageController {
    private IMortgageView view;

    public MortgageGUIController(IMortgageView v) {
        view = v;
    }

    public void submitApplication() {
        //Initialization values
        double inc = 0;
        double mDebt = 0;
        int cScore = 0;
        double costOfHome = 0;
        double downPayment = 0;
        int years = 0;

        //Asking for customer information/////////////////////
        String name = view.getName();
        String error = "";

        //Validation for yearly income - greater than 0
        inc = view.getYearlyIncome();
        if(inc <= 0){
            error += "Income must be greater than 0.";
        }

        //Validation for monthly debt - greater or equal to 0
        mDebt = view.getMonthlyDebt();
        if(mDebt < 0 && error.length() == 0){
            error += "Debt must be greater than or equal to 0.";
        }

        //Validation for credit score value - between 0 and 850
        cScore = view.getCreditScore();
        if((cScore <= 0 || cScore >= 850) && error.length() == 0){
            error += "Credit Score must be greater than 0 and less than 850";
        }

        //Assigning customer based on monthly debt, income, credit score, and name
        ICustomer iC = new Customer(mDebt, inc, cScore, name);

        //Asking for Mortgage information/////////////////////
        //Validation for house cost - greater than 0
        costOfHome = view.getHouseCost();
        if (costOfHome <= 0 && error.length() == 0) {
            error += "Cost must be greater than 0.";
        }

        //Validation for down payment of house - between 0 and cost of house
        downPayment = view.getDownPayment();
        if ((downPayment <= 0 || downPayment >= costOfHome) && error.length() == 0) {
            error += "Down Payment must be greater than 0 and less than the cost of the house.";
        }

        //Validation for years - greater than 0
        years = view.getYears();
        if (years <= 0 && error.length() == 0) {
            error += "Years must be greater than 0.";
        }

        //Assigning mortgage on customer based on cost of home, down payment for home, and term length
        IMortgage iM = new Mortgage(costOfHome, downPayment, years, iC);

        // If an error message was produced, send to view
        if (error.length() > 0)
            view.printToUser(error);
        // If no error, send info to view
        else {
            view.displayApproved(iM.loanApproved());
            view.displayRate(iM.getRate());
            view.displayPayment(iM.getPayment());
        }
    }
}