import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class Project {
    private int empID;
    private int projectId;
    private Date dateFrom;
    private Date dateTo;
    private Map<Integer,Employee> employeeMap;

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
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

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return empID == project.empID &&
                projectId == project.projectId &&
                dateFrom.equals(project.dateFrom) &&
                dateTo.equals(project.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empID, projectId, dateFrom, dateTo);
    }

    @Override
    public String toString() {
        return "Project{" +
                "empID=" + empID +
                ", projectId=" + projectId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
