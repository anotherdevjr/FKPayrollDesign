package utils;

public class EmployeeIDGenerator {
    /**
     * This method generates a new Employee Id.
     * More sophisticated methods could be formed for this as
     * in the following there are chances of collision.
     * @param beginningString The unique starting code for different type of Employees
     * @param length The length of digits we need to randomly generate
     * @return The unique ID generated from the requisite param configurations
     */
    public static String getEmployeeId(String beginningString, int length) {
        String digits = "0123456789";
        StringBuilder generator = new StringBuilder(length + beginningString.length());
        generator.append(beginningString);

        for (int i = 0; i < length; i++) {
            int index = (int) (digits.length() * Math.random());
            generator.append(digits.charAt(index));
        }
        return generator.toString();
    }
}
