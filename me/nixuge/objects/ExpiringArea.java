package me.nixuge.objects;

import me.nixuge.objects.maths.Area;

public class ExpiringArea {
    private final Area area;
    private final int breakTime;
    private final int breakStartTime;

    public ExpiringArea(Area area, int breakTime, int breakStartTime) {
        this.area = area;
        this.breakTime = breakTime;
        this.breakStartTime = breakStartTime;
    }
    
    public Area getArea() {
        return area;
    }
    public int getBreakTime() {
        return breakTime;
    }
    public int getBreakStartTime() {
        return breakStartTime;
    }
}
