package com.gusnavanila.peluang.Model;

public class WeekReport {
    public int income, outcome, deviation;

    public WeekReport(int income, int outcome, int deviation) {
        this.income = income;
        this.outcome = outcome;
        this.deviation = deviation;
    }

    public void changeText(int trigger) {
        this.deviation = trigger;
    }
}
