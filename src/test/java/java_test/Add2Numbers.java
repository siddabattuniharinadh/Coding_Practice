package java_test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Add2Numbers 
{
    public static void main(String[] args) 
    {
        try (Scanner scanner = new Scanner(System.in)) 
        {
            System.out.println("========================================");
            System.out.println("  ADD NUMBERS - ALL NUMBER TYPES SUPPORTED");
            System.out.println("========================================");
            // 1) Take inputs dynamically from the user
            System.out.print("How many numbers do you want to add? ");
            int count = readValidCount(scanner);
            List<String> rawInputs = new ArrayList<>();
            for (int i = 1; i <= count; i++) 
            {
                System.out.print("Enter number #" + i + " (int, long, float, double, decimal): ");
                rawInputs.add(scanner.next().trim());
            }
            // 2) Process each input: classify and add all types of numbers
            Number sum = addAllNumbers(rawInputs);
            // Display the numbers entered
            System.out.println("\nNumbers entered:");
            for (String raw : rawInputs) 
            {
                System.out.println("  -> " + raw);
            }
            printFormattedResult(sum);
        } 
        catch (Exception e) 
        {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
    private static int readValidCount(Scanner scanner) 
    {
        while (!scanner.hasNextInt()) 
        {
            System.out.print("That's not a valid number. Please enter a whole number: ");
            scanner.next();
        }
        int count = scanner.nextInt();
        while (count <= 0) 
        {
            System.out.print("Count must be greater than 0. Please re-enter: ");
            while (!scanner.hasNextInt()) 
            {
                System.out.print("That's not a valid number. Please enter a whole number: ");
                scanner.next();
            }
            count = scanner.nextInt();
        }
        return count;
    }
    private static Number addAllNumbers(List<String> rawInputs) 
    {
        boolean hasDecimal = false;
        boolean hasBigDecimal = false;
        boolean needsLong = false;
        List<Number> parsed = new ArrayList<>();
        for (String raw : rawInputs) 
        {
            if (isInteger(raw)) 
            {
                long longValue = Long.parseLong(raw);
                if (longValue < Integer.MIN_VALUE || longValue > Integer.MAX_VALUE) 
                {
                    needsLong = true;
                }
                parsed.add(longValue);
                continue;
            }
            try 
            {
                double doubleValue = Double.parseDouble(raw);
                hasDecimal = true;
                if (isHighPrecisionDecimal(raw)) 
                {
                    hasBigDecimal = true;
                    parsed.add(BigDecimal.ZERO);
                }
                else 
                {
                    parsed.add(doubleValue);
                }
            } 
            catch (NumberFormatException e2) 
            {
                System.out.println("Skipping invalid number: \"" + raw + "\" (not a valid numeric value)");
            }
        }
        if (hasBigDecimal) 
        {
            return addBigDecimals(rawInputs);
        }
        Number result = hasDecimal ? 0.0 : 0;
        for (Number num : parsed) 
        {
            result = add(result, num);
        }
        if (!hasDecimal) 
        {
            if (needsLong) 
            {
                return result.longValue();
            }
            return result.intValue();
        }
        return result;
    }
    private static boolean isInteger(String raw) 
    {
        try 
        {
            Long.parseLong(raw);
            return true;
        } 
        catch (NumberFormatException e) 
        {
            return false;
        }
    }
    private static boolean isHighPrecisionDecimal(String raw) 
    {
        String s = raw.toLowerCase().replaceFirst("^[+-]", "");
        String mantissa = s.contains("e") ? s.substring(0, s.indexOf('e')) : s;
        String digitsOnly = mantissa.replace(".", "");
        return digitsOnly.length() > 15;
    }
    private static BigDecimal addBigDecimals(List<String> rawInputs) 
    {
        BigDecimal result = BigDecimal.ZERO;
        for (String raw : rawInputs) 
        {
            try 
            {
                result = result.add(new BigDecimal(raw.trim()));
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Skipping invalid number: \"" + raw + "\" (not a valid numeric value)");
            }
        }
        return result;
    }
    private static void printFormattedResult(Number sum) 
    {
        System.out.println("\n----------------------------------------");
        System.out.println("actual output => SUM  = " + sum);
        System.out.println("----------------------------------------");
        System.out.println("int    : " + sum.intValue());
        System.out.println("long   : " + sum.longValue());
        System.out.println("float  : " + sum.floatValue());
        System.out.println("double : " + sum.doubleValue());
        System.out.println("decimal: " + (sum instanceof BigDecimal ? sum : new BigDecimal(sum.toString())));
        System.out.println("----------------------------------------");
        System.out.println("Sum type     : " + sum.getClass().getSimpleName());
        System.out.println("Integer sum? : " + (sum instanceof Integer || sum instanceof Long));
        System.out.println("Decimal sum? : " + (sum instanceof Float || sum instanceof Double || sum instanceof BigDecimal));
    }
    public static int add(int a, int b) 
    {
        return a + b;
    }
    public static long add(long a, long b) 
    {
        return a + b;
    }
    public static float add(float a, float b) 
    {
        return a + b;
    }
    public static double add(double a, double b) 
    {
        return a + b;
    }
    public static BigDecimal add(BigDecimal a, BigDecimal b) 
    {
        return a.add(b);
    }
    public static long add(int a, long b) 
    {
        return a + b;
    }
    public static long add(long a, int b) 
    {
        return a + b;
    }
    public static double add(int a, double b) 
    {
        return a + b;
    }
    public static double add(double a, int b) 
    {
        return a + b;
    }
    public static double add(long a, double b) 
    {
        return a + b;
    }
    public static double add(double a, long b) 
    {
        return a + b;
    }
    public static double add(float a, double b) 
    {
        return a + b;
    }
    public static double add(double a, float b) 
    {
        return a + b;
    }
    public static float add(int a, float b) 
    {
        return a + b;
    }
    public static float add(float a, int b) 
    {
        return a + b;
    }
    public static float add(long a, float b) 
    {
        return a + b;
    }
    public static float add(float a, long b) 
    {
        return a + b;
    }
    private static Number add(Number a, Number b) 
    {
        boolean aIsDecimal = isDecimalType(a);
        boolean bIsDecimal = isDecimalType(b);
        if (!aIsDecimal && !bIsDecimal) 
        {
            long sum = a.longValue() + b.longValue();
            if (sum >= Integer.MIN_VALUE && sum <= Integer.MAX_VALUE) 
            {
                return (int) sum;
            }
            return sum;
        }
        if (isFloatType(a) && isFloatType(b)) 
        {
            return a.floatValue() + b.floatValue();
        }
        return a.doubleValue() + b.doubleValue();
    }
    private static boolean isDecimalType(Number n) 
    {
        return n instanceof Float || n instanceof Double || n instanceof BigDecimal;
    }
    private static boolean isFloatType(Number n) 
    {
        return n instanceof Float;
    }
}