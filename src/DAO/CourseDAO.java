package DAO;

import java.util.List;

public interface CourseDAO {
    public List<Beans.Course> findQualified(int dept_id, int timeslot_id);
}
