package de.bassadin;

public class Workpiece {
    private final double workpieceSize;
    private final int workpieceNumber;

    public Workpiece(double workPieceSize, int workpieceNumber) {
        this.workpieceSize = workPieceSize;
        this.workpieceNumber = workpieceNumber;
    }

    public double getWorkpieceSize() {
        return workpieceSize;
    }

    public int getWorkpieceNumber() {
        return workpieceNumber;
    }

    @Override
    public String toString() {
        return "Workpiece{" +
                "workpieceSize=" + workpieceSize +
                ", workpieceNumber=" + workpieceNumber +
                '}';
    }
}
