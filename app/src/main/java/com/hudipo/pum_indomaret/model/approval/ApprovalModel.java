package com.hudipo.pum_indomaret.model.approval;

public class ApprovalModel {
    private String pumNumber;
    private String pumRequester;
    private int amount;
    private boolean isChecked;

    public void setPumNumber(String pumNumber) {
        this.pumNumber = pumNumber;
    }

    public void setPumRequester(String pumRequester) {
        this.pumRequester = pumRequester;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPumNumber() {
        return pumNumber;
    }

    public String getPumRequester() {
        return pumRequester;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
