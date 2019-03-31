package in.edu.kssem360.Model;

public class AboutDeveloperModelClass {

    private String name, department, year;
    private int image;

    public AboutDeveloperModelClass() {
    }

    public AboutDeveloperModelClass(String name, String department, String year, int image) {
        this.name = name;
        this.department = department;
        this.year = year;
        this.image = image;
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


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
