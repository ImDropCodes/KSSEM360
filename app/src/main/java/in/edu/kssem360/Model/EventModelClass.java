package in.edu.kssem360.Model;

public class EventModelClass {
    private String name, image, department, date, venue, type, fee, num_part, teacher_co, student_co, co_ord_number, desc, admin;

    public EventModelClass() {
    }

    public EventModelClass(String name, String image, String department, String date, String venue, String type, String fee, String num_part, String teacher_co, String student_co, String co_ord_number, String desc, String admin) {
        this.name = name;
        this.image = image;
        this.department = department;
        this.date = date;
        this.venue = venue;
        this.type = type;
        this.fee = fee;
        this.num_part = num_part;
        this.teacher_co = teacher_co;
        this.student_co = student_co;
        this.co_ord_number = co_ord_number;
        this.desc = desc;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getNum_part() {
        return num_part;
    }

    public void setNum_part(String num_part) {
        this.num_part = num_part;
    }

    public String getTeacher_co() {
        return teacher_co;
    }

    public void setTeacher_co(String teacher_co) {
        this.teacher_co = teacher_co;
    }

    public String getStudent_co() {
        return student_co;
    }

    public void setStudent_co(String student_co) {
        this.student_co = student_co;
    }

    public String getCo_ord_number() {
        return co_ord_number;
    }

    public void setCo_ord_number(String co_ord_number) {
        this.co_ord_number = co_ord_number;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}