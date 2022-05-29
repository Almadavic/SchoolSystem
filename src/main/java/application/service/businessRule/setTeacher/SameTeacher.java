package application.service.businessRule.setTeacher;


import application.entity.users.Teacher;
import application.service.exception.classRoomService.SameTeacherException;


public class SameTeacher implements SetTeacherCheck {
    @Override

    public void validation(Teacher teacher, Teacher classTeacher) {
        if(classTeacher!=null) {
            if (classTeacher.getId() == teacher.getId()) {
                throw new SameTeacherException("The new teacher cannot be the same than the last teacher!");
            }
        }
    }
}
