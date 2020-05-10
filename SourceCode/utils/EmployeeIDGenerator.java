package utils;

public class EmployeeIDGenerator {
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
