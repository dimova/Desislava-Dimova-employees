import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        List<XPPair> xpPairs = new ArrayList<>();

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
        boolean isOverlap;
        for (Map.Entry<Integer, List<Project>> entry : byProID.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
            List<Project> prosById = entry.getValue();
            for (int i = 0; i < prosById.size(); i++) {
                for (int j = i+1; j < prosById.size(); j++) {
                    // compare list.get(i) and list.get(j)
                    Project a = prosById.get(i);
                    Project b = prosById.get(j);
                    isOverlap = isOverlapping(prosById.get(i), prosById.get(j));
                    if (isOverlap){
                        System.out.println("isOverlap:" + isOverlap);
                        XPPair xpp = new XPPair();
                        Employee e1 = new Employee();
                        e1.setEmpID(a.getEmpID());
                        e1.setProjectID(a.getProjectId());
                        e1.setDateFrom(a.getDateFrom());
                        e1.setDateTo(a.getDateTo());
                        Employee e2 = new Employee();
                        e2.setEmpID(b.getEmpID());
                        e2.setProjectID(b.getProjectId());
                        e2.setDateFrom(b.getDateFrom());
                        e2.setDateTo(b.getDateTo());
                        xpp.setEmployeeA(e1);
                        xpp.setEmployeeB(e2);
                        xpp.setEmpAId(Integer.toString(e1.getEmpID()));
                        xpp.setEmpBId(Integer.toString(e2.getEmpID()));
                        if (e1.getProjectID() == e2.getProjectID()) {
                            xpp.setProjectId(Integer.toString(a.getProjectId()));
                        }
                        xpp.setTotalDaysWorkedTogether(overlapCalculated(e1.getDateFrom(), e1.getDateTo(),
                                e2.getDateFrom(), e2.getDateTo()));
                        xpPairs.add(xpp);

                    }
                }
            }

        }
        for(XPPair xpp : xpPairs){
            System.out.println(xpp.toString());
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

                            dateFrom = new Date(System.currentTimeMillis());
                        } else {
                            dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(res[2]);
                        }
                        employee.setDateFrom(dateFrom);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        if (res[3].equalsIgnoreCase("NULL")) {

                            dateTo = new Date(System.currentTimeMillis());
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

    public static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && start2.before(end1);
    }
    public static boolean isOverlapping(Project a, Project b){
        return isOverlapping(a.getDateFrom(),a.getDateTo(),b.getDateFrom(),b.getDateTo());
    }


    public static String numberOfDays(Date d1, Date d2) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strD1 = dateFormat.format(d1);
        String strD2 = dateFormat.format(d2);
        //Parsing the date
        LocalDate dateBefore = LocalDate.parse(strD1);
        LocalDate dateAfter = LocalDate.parse(strD2);

        //calculating number of days in between
        long numOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);

        //displaying the number of days
        System.out.println(numOfDaysBetween);
        return Long.toString(numOfDaysBetween);

    }

    protected static String overlapCalculated(Date aStart, Date aEnd,
                                              Date bStart, Date bEnd) {
        //To find the actual overlap range, you take the maximum of the two low ends, and the minimum of the two high ends:
        //int e = Math.max(aStart,bStart);
        Date e;
        if (aStart.before(bStart)) {
            e = bStart;
        } else {
            e = aStart;
        }
        //int f = Math.min(aEnd,bEnd);
        Date f;
        if (aEnd.before(bEnd)) {
            f = aEnd;
        } else {
            f = bEnd;
        }
        // overlapping range is [e,f], and overlap exists if e <= f.
        //All above assumes that the ranges are inclusive, that is, the range defined by aStart and aEnd includes both the value of aStart and the value of aEnd.
        // It is fairly trivial to adjust for exclusive ranges, however.
        //return e <= f;
        String daysOfWorkTogether;
        daysOfWorkTogether = numberOfDays(e, f);
        return daysOfWorkTogether;
    }


    public static boolean overlappingPeriods(Date aStart, Date aEnd,
                                             Date bStart, Date bEnd) {
        if (aStart.after(aEnd)) {
            throw new IllegalArgumentException("A start can not be after its end.");
        }
        if (bStart.after(bEnd)) {
            throw new IllegalArgumentException("B start can not be after its end.");
        }
        return !((aEnd.before(bStart) && aStart.before(bStart)) ||
                (bEnd.before(aStart) && bStart.before(aStart)));
    }

    protected static boolean overlapRanged(Date aStart, Date aEnd,
                                           Date bStart, Date bEnd) {
        //To find the actual overlap range, you take the maximum of the two low ends, and the minimum of the two high ends:
        //int e = Math.max(aStart,bStart);
        int e;
        if (aStart.before(bStart)) {
            e = (int) bStart.getTime();
        } else {
            e = (int) aStart.getTime();
        }
        //int f = Math.min(aEnd,bEnd);
        int f;
        if (aEnd.before(bEnd)) {
            f = (int) aEnd.getTime();
        } else {
            f = (int) bEnd.getTime();
        }
        // overlapping range is [e,f], and overlap exists if e <= f.
        //All above assumes that the ranges are inclusive, that is, the range defined by aStart and aEnd includes both the value of aStart and the value of aEnd.
        // It is fairly trivial to adjust for exclusive ranges, however.
        return e <= f;
    }

    public static void main(String[] args) throws ParseException {
        // get the file url, not working in JAR file.
        File testFile = getResource(resource);
        printFile(testFile);
        //readToEmployees(testFile);
        readToProjects(testFile);

    }
}
