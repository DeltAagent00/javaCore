package Lesson_4;

public enum StatusEnum {
    One,
    Two,
    Three;

    public static StatusEnum getNextStatus(StatusEnum currentStatus) {
        final int position = currentStatus.ordinal();
        final int next = (position + 1) % StatusEnum.values().length;
        return StatusEnum.values()[next];
    }
}
