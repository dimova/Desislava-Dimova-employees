import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class XPPairEmps {
    private static URL resource = XPPairEmps.class.getClassLoader().getResource("employees.txt");
    private static Map<Integer, Employee> employees = new HashMap<Integer,Employee>();
    private static List<Employee> emps = new ArrayList<>();
    private static Project projectNum = new Project();


    public static File getResource(URL resource) {
        File resourceFile = null;
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            try {
                resourceFile = new File(resource.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return resourceFile;
    }

    private static void printFile(File file) {

        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void readToProjects(File file) {
        BufferedReader reader = null;
        Employee employee = new Employee();
        Project projectStructure = new Project();
        Date dateFrom;
        Date dateTo;
        try {
            try {
                reader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String line = new String();
            while (true) {
                try {
                    if (!((line = reader.readLine()) != null)) break;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                while (true) {
                    try {
                        if (!((line = reader.readLine()) != null)) break;
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                //your code
                System.out.println(line);
                // read next line

                String[] res = line.split("[,]", 0);
                for (int i = 0; i < res.length; i++) {
                    res[i] = res[i].trim();
                }

                projectNum.setEmpID(Integer.parseInt(res[0]));
                projectNum.setProjectId(Integer.parseInt(res[1]));
                try {
                    if (res[2].equalsIgnoreCase("NULL")) {
                        long millis = System.currentTimeMillis();
                        java.util.Date date = new java.util.Date(millis);
                        dateFrom = date;
                    } else {
                        dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(res[2]);
                    }
                    projectNum.setDateFrom(dateFrom);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    if (res[3].equalsIgnoreCase("NULL")) {
                        long millis = System.currentTimeMillis();
                        java.util.Date date = new java.util.Date(millis);
                        dateTo = date;
                    } else {
                        dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(res[3]);
                    }
                    projectNum.setDateTo(dateTo);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //employees.put(employee.getEmpID(), employee);
                //emps.add(employee);

                System.out.println("Employee ID: " + projectNum.getEmpID());
                System.out.println("Project ID: " + projectNum.getProjectId());
                System.out.println("Date From: " + projectNum.getDateFrom());
                System.out.println("Date To: " + projectNum.getDateTo());



            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void readToEmployees(File file) {
        BufferedReader reader = null;
        Employee employee = new Employee();
        Date dateFrom;
        Date dateTo;
        LinkedHashSet<Employee> setOfEmployees=new LinkedHashSet<Employee>();


        try {
            reader = new BufferedReader(new FileReader(file));
            String line = new String();
            while (true) {
                try {
                    if (!((line = reader.readLine()) != null)) break;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                while (true) {
                    try {
                        if (!((line = reader.readLine()) != null)) break;
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    //your code
                    System.out.println(line);
                    // read next line

                    String[] res = line.split("[,]", 0);
                    for (int i = 0; i < res.length; i++) {
                        res[i] = res[i].trim();
                    }
                    employee.setEmpID(Integer.parseInt(res[0]));
                    employee.setProjectID(Integer.parseInt(res[1]));
                    try {
                        if (res[2].equalsIgnoreCase("NULL")) {
                            long millis = System.currentTimeMillis();
                            java.util.Date date = new java.util.Date(millis);
                            dateFrom = date;
                        } else {
                            dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(res[2]);
                        }
                        employee.setDateFrom(dateFrom);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        if (res[3].equalsIgnoreCase("NULL")) {
                            long millis = System.currentTimeMillis();
                            java.util.Date date = new java.util.Date(millis);
                            dateTo = date;
                        } else {
                            dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(res[3]);
                        }
                        employee.setDateTo(dateTo);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //employees.put(employee.getEmpID(), employee);
                    //emps.add(employee);
                    setOfEmployees.add(employee);
                    System.out.println("Employee ID: " + employee.getEmpID());
                    System.out.println("Project ID: " + employee.getProjectID());
                    System.out.println("Date From: " + employee.getDateFrom());
                    System.out.println("Date To: " + employee.getDateTo());


                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<Employee> itr = setOfEmployees.iterator();
        while (itr.hasNext()) {
            Employee e = itr.next();
            System.out.println(e.getProjectID());
            System.out.println(e.getEmpID());
        }
    }

    private static void overlapRange(){
        //To find the actual overlap range, you take the maximum of the two low ends, and the minimum of the two high ends:
        //int e = Math.max(a,b);
        //int f = Math.min(c,d);
        // overlapping range is [e,f], and overlap exists if e <= f.
        //All above assumes that the ranges are inclusive, that is, the range defined by a and c includes both the value of a and the value of c.
        // It is fairly trivial to adjust for exclusive ranges, however.
    }

    public static void main(String[] args) {
        // get the file url, not working in JAR file.
        File testFile = getResource(resource);
        printFile(testFile);
        readToEmployees(testFile);

    }
}
