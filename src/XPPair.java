import java.util.List;
import java.util.Objects;

public class XPPair {
    private String empAId;
    private String empBId;
    private String projectId;
    private Employee employeeA;
    private Employee employeeB;
    private String totalDaysWorkedTogether;

    public String getEmpAId() {
        return empAId;
    }

    public void setEmpAId(String empAId) {
        this.empAId = empAId;
    }

    public String getEmpBId() {
        return empBId;
    }

    public void setEmpBId(String empBId) {
        this.empBId = empBId;
    }

    public Employee getEmployeeA() {
        return employeeA;
    }

    public void setEmployeeA(Employee employeeA) {
        this.employeeA = employeeA;
    }

    public Employee getEmployeeB() {
        return employeeB;
    }

    public void setEmployeeB(Employee employeeB) {
        this.employeeB = employeeB;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTotalDaysWorkedTogether() {
        return totalDaysWorkedTogether;
    }

    public void setTotalDaysWorkedTogether(String totalDaysWorkedTogether) {
        this.totalDaysWorkedTogether = totalDaysWorkedTogether;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XPPair)) return false;
        XPPair xpPair = (XPPair) o;
        return getEmpAId().equals(xpPair.getEmpAId()) &&
                getEmpBId().equals(xpPair.getEmpBId()) &&
                Objects.equals(getProjectId(), xpPair.getProjectId()) &&
                getEmployeeA().equals(xpPair.getEmployeeA()) &&
                getEmployeeB().equals(xpPair.getEmployeeB()) &&
                Objects.equals(getTotalDaysWorkedTogether(), xpPair.getTotalDaysWorkedTogether());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmpAId(), getEmpBId(), getProjectId(), getEmployeeA(), getEmployeeB(), getTotalDaysWorkedTogether());
    }

    @Override
    public String toString() {
        return "XPPair{" +
                "empAId='" + empAId + '\'' +
                ", empBId='" + empBId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", employeeA=" + employeeA +
                ", employeeB=" + employeeB +
                ", totalDaysWorkedTogether='" + totalDaysWorkedTogether + '\'' +
                '}';
    }
}
