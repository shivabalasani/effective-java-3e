package effectivejava.chapter6.item34;

/**
 * 
 * This code is undeniably concise, but it is dangerous from a maintenance
 * perspective. Suppose you add an element to the enum, perhaps a special value
 * to represent a vacation day, but forget to add a corresponding case to the
 * switch statement. The program will still compile, but the pay method will
 * silently pay the worker the same amount for a vacation day as for an ordinary
 * weekday.
 *
 */

// Enum that switches on its value to share code - questionable
public enum PayrollDayDeficient {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    private static final int MINS_PER_SHIFT = 8 * 60;

    int pay(int minutesWorked, int payRate) {
	int basePay = minutesWorked * payRate;
	int overtimePay;
	switch (this) {
	case SATURDAY:
	case SUNDAY: // Weekend
	    overtimePay = basePay / 2;
	    break;
	default: // Weekday
	    overtimePay = minutesWorked <= MINS_PER_SHIFT ? 0 : (minutesWorked - MINS_PER_SHIFT) * payRate / 2;
	}
	return basePay + overtimePay;
    }
}
