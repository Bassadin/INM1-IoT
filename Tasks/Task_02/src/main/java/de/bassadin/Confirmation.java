package de.bassadin;

public class Confirmation {
    private final double deviation;
    private final int workpieceNumber;

    private final boolean isOK;

    public Confirmation(double deviation, int workpieceNumber, boolean isOK) {
        this.deviation = deviation;
        this.workpieceNumber = workpieceNumber;
        this.isOK = isOK;
    }

    public double getDeviation() {
        return deviation;
    }

    public int getWorkpieceNumber() {
        return workpieceNumber;
    }

    public boolean isOK() {
        return isOK;
    }

    @Override
    public String toString() {
        return "Confirmation{" +
                "deviation=" + deviation +
                ", workpieceNumber=" + workpieceNumber +
                ", isOK=" + isOK +
                '}';
    }
}
