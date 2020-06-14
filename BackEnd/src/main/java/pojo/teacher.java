package pojo;

public class teacher {
    private int teaId;
    private String teaUsername;
    private String teaPassword;

    public teacher() {
    }

    public teacher(int teaId, String teaUsername, String teaPassword) {
        this.teaId = teaId;
        this.teaUsername = teaUsername;
        this.teaPassword = teaPassword;
    }

    public int getTeaId() {
        return teaId;
    }

    public void setTeaId(int teaId) {
        this.teaId = teaId;
    }

    public String getTeaUsername() {
        return teaUsername;
    }

    public void setTeaUsername(String teaUsername) {
        this.teaUsername = teaUsername;
    }

    public String getTeaPassword() {
        return teaPassword;
    }

    public void setTeaPassword(String teaPassword) {
        this.teaPassword = teaPassword;
    }
}
