package restsample;

import java.util.Objects;

public class Issues {

    private int id;
    private String subject;
    private String description;
    private String state_name;

    public String getState_name() {
        return state_name;
    }

    public Issues withStatus(String status) {
        this.state_name = status;
        return this;
    }

    public int getId() {
        return id;
    }

    public Issues withId(int id) {
        this.id = id;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Issues withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Issues withDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issues issues = (Issues) o;

        if (id != issues.id) return false;
        if (!Objects.equals(subject, issues.subject)) return false;
        if (!Objects.equals(description, issues.description)) return false;
        return Objects.equals(state_name, issues.state_name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (state_name != null ? state_name.hashCode() : 0);
        return result;
    }

}
