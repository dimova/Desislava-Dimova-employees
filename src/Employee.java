import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class Employee {
    private int empID;
    private int projectID;
    private Date dateFrom;
    private Date dateTo;
    private Map<Integer, Employee> employees;
    private int workTime;

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Employee() {
    }

    public int compareTo(Employee o) {

        if (o.getProjectID() > this.getProjectID()) {
            return 1;
        } else if (o.getProjectID() < this.getProjectID()) {
            return -1;
        }

        return 0;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return getEmpID() == employee.getEmpID() &&
                getProjectID() == employee.getProjectID() &&
                getWorkTime() == employee.getWorkTime() &&
                getDateFrom().equals(employee.getDateFrom()) &&
                getDateTo().equals(employee.getDateTo()) &&
                Objects.equals(employees, employee.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmpID(), getProjectID(), getDateFrom(), getDateTo(), employees, getWorkTime());
    }


}
