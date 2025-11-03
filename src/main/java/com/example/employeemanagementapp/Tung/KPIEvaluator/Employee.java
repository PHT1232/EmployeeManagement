public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String position;
    private double salary;
    private double totalHoursMonth;
    private double bonusHoursMonth;
    private double monthlySalary;

    // ✨ Constructors
    public Employee() {}

    public Employee(int employeeId, String firstName, String lastName, double salary) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public Employee(int employeeId, String firstName, String lastName, String email,
                    String phone, String position, double salary,
                    double totalHoursMonth, double bonusHoursMonth, double monthlySalary) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.salary = salary;
        this.totalHoursMonth = totalHoursMonth;
        this.bonusHoursMonth = bonusHoursMonth;
        this.monthlySalary = monthlySalary;
    }

    // ✨ Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getTotalHoursMonth() {
        return totalHoursMonth;
    }
    public void setTotalHoursMonth(double totalHoursMonth) {
        this.totalHoursMonth = totalHoursMonth;
    }

    public double getBonusHoursMonth() {
        return bonusHoursMonth;
    }
    public void setBonusHoursMonth(double bonusHoursMonth) {
        this.bonusHoursMonth = bonusHoursMonth;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }
    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    // 🩷 Optional helper
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return String.format(
            "Employee[id=%d, name=%s %s, position=%s, baseSalary=%.2f, totalHours=%.2f, bonusHours=%.2f, monthlySalary=%.2f]",
            employeeId, firstName, lastName, position, salary, totalHoursMonth, bonusHoursMonth, monthlySalary
        );
    }
}
