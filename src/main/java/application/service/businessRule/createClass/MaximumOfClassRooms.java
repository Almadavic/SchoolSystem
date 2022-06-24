package application.service.businessRule.createClass;

import application.domain.entity.ClassRoom;
import application.service.exception.classRoomService.MaximumOfClassRoomsException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaximumOfClassRooms implements CreateClassCheck {

    @Override
    public void validation(List<ClassRoom> classRooms) {

        int size = classRooms.size();

        if (size >= 19) {
            throw new MaximumOfClassRoomsException("The system can't have more than 19 classrooms");
        }

    }
}

