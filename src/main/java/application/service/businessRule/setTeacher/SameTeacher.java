package application.service.businessRule.setTeacher;


import application.entity.user.Teacher;
import application.service.exception.classRoomService.SameTeacherException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SameTeacher implements SetTeacherCheck { // O professor não pode ser igual ao que já era
    @Override
    public void validation(Teacher teacher, Teacher classTeacher) {
        if (classTeacher != null) {
            if (classTeacher.getId() == teacher.getId()) {
                throw new SameTeacherException("The new teacher cannot be the same than the last teacher!");
            }
        }
    }
}
