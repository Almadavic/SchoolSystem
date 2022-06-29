package application.service.businessRule.createClass;

import application.entity.ClassRoom;
import application.service.exception.classRoomService.MaximumOfClassRoomsException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
public class MaximumOfClassRooms implements CreateClassCheck {

    @Override
    public void validation(List<ClassRoom> classRooms) {

        int size = classRooms.size();

        if (size >= 19) {
            throw new MaximumOfClassRoomsException("The system can't have more than 19 classrooms");
        }

    }
}

