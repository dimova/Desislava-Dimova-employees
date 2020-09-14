import java.util.HashMap;
import java.util.Map;

public class EmployeeStore {
    private Map<Integer, Employee> employees = new HashMap<>();

    public EmployeeStore() {
    }

    public void add(Employee e) {
        Employee in = employees.put(e.getEmpID(), e);
    }

    public Employee searchByEmpID(Integer id) {
        Employee employee = employees.get(id);
        return employee;
    }

}
