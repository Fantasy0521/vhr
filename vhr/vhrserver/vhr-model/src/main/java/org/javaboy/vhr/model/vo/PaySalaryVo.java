package org.javaboy.vhr.model.vo;

public class PaySalaryVo {

    private Float finalSalary;

    private Float shouldPaySalary;

    private Float pension;

    private Float medical;

    private Float fund;

    public Float getFinalSalary() {
        return finalSalary;
    }

    public void setFinalSalary(Float finalSalary) {
        this.finalSalary = finalSalary;
    }

    public Float getShouldPaySalary() {
        return shouldPaySalary;
    }

    public void setShouldPaySalary(Float shouldPaySalary) {
        this.shouldPaySalary = shouldPaySalary;
    }

    public Float getPension() {
        return pension;
    }

    public void setPension(Float pension) {
        this.pension = pension;
    }

    public Float getMedical() {
        return medical;
    }

    public void setMedical(Float medical) {
        this.medical = medical;
    }

    public Float getFund() {
        return fund;
    }

    public void setFund(Float fund) {
        this.fund = fund;
    }
}
