package in.edu.kssem360.Model;

public class AboutDeveloperModelClass {

    private String name, department, year;

    public AboutDeveloperModelClass() {
    }

    public AboutDeveloperModelClass(String name, String department, String year) {
        this.name = name;
        this.department = department;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
