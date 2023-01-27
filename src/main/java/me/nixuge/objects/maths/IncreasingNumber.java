package me.nixuge.objects.maths;

public class IncreasingNumber {
    private int number;

    public IncreasingNumber() {
        this.number = 0;
    }
    public IncreasingNumber(int startingNumber) {
        this.number = startingNumber;
    }

    public int getNumber() {
        number++;
        return number;
    }
    public void skipNumber() {
        number++;
    }
}
