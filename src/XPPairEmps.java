import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class XPPairEmps {
    private static final URL resource = XPPairEmps.class.getClassLoader().getResource("employees.txt");

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
        ArrayList<Project> arrayListProjects = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Date dateFrom;
        Date dateTo;

        Map<Integer, List<Project>> byProID
                = new HashMap<>();


        try {

            assert reader != null;
            reader.readLine();
            while (true) {

                String line;
                if ((line = reader.readLine()) == null) break;

                String[] res = line.split("[,]", 0);
                for (int i = 0; i < res.length; i++) {
                    res[i] = res[i].trim();
                }
                Project projectNum = new Project();
                projectNum.setEmpID(Integer.parseInt(res[0]));
                projectNum.setProjectId(Integer.parseInt(res[1]));

                if (res[2].equalsIgnoreCase("NULL")) {
                    long millis = System.currentTimeMillis();
                    dateFrom = new Date(millis);
                } else {
                    dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(res[2]);
                }
                projectNum.setDateFrom(dateFrom);

                if (res[3].equalsIgnoreCase("NULL")) {
                    long millis = System.currentTimeMillis();
                    dateTo = new Date(millis);
                } else {
                    dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(res[3]);
                }
                projectNum.setDateTo(dateTo);
                arrayListProjects.add(projectNum);
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        for (Project arrayListProject : arrayListProjects) {
            byProID.computeIfAbsent(arrayListProject.getProjectId(), k -> new ArrayList<>()).add(arrayListProject);
        }

        for (Map.Entry<Integer, List<Project>> entry : byProID.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }


    }

    private static void readToEmployees(File file) {
        BufferedReader reader;
        Employee employee = new Employee();
        Date dateFrom;
        Date dateTo;
        LinkedHashSet<Employee> setOfEmployees = new LinkedHashSet<>();


        try {
            reader = new BufferedReader(new FileReader(file));
            while (true) {
                String line = "";
                try {
                    if ((line = reader.readLine()) == null) break;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                while (true) {
                    try {
                        if ((line = reader.readLine()) == null) break;
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    String[] res = line.split("[,]", 0);
                    for (int i = 0; i < res.length; i++) {
                        res[i] = res[i].trim();
                    }
                    employee.setEmpID(Integer.parseInt(res[0]));
                    employee.setProjectID(Integer.parseInt(res[1]));
                    try {
                        if (res[2].equalsIgnoreCase("NULL")) {
                            long millis = System.currentTimeMillis();
                            dateFrom = new Date(millis);
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
                            dateTo = new Date(millis);
                        } else {
                            dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(res[3]);
                        }
                        employee.setDateTo(dateTo);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
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
    }
    public static boolean OverlappingPeriods(Date aStart, Date aEnd,
                                          Date bStart, Date bEnd)
    {
        if (aStart.after(aEnd)) {
            throw new IllegalArgumentException("A start can not be after its end.");
        }
        if(bStart.after(bEnd)) {
            throw new IllegalArgumentException("B start can not be after its end.");
        }
        return !((aEnd.before(bStart) && aStart.before(bStart)) ||
                (bEnd.before(aStart) && bStart.before(aStart)));
    }

    public static boolean overlapRange(Date aStart, Date aEnd,
                                       Date bStart, Date bEnd) {
        //To find the actual overlap range, you take the maximum of the two low ends, and the minimum of the two high ends:
        //int e = Math.max(aStart,bStart);
        int e;
        if(aStart.before(bStart)){ e = (int) bStart.getTime();} else { e = (int) aStart.getTime();}
        //int f = Math.min(aEnd,bEnd);
        int f;
        if(aEnd.before(bEnd)){ f = (int) aEnd.getTime();} else { f = (int) bEnd.getTime();}
        // overlapping range is [e,f], and overlap exists if e <= f.
        //All above assumes that the ranges are inclusive, that is, the range defined by aStart and aEnd includes both the value of aStart and the value of aEnd.
        // It is fairly trivial to adjust for exclusive ranges, however.
        return e<=f;
    }

    public static void main(String[] args) {
        // get the file url, not working in JAR file.
        File testFile = getResource(resource);
        //printFile(testFile);
        //readToEmployees(testFile);
        readToProjects(testFile);


    }
}
