package application.services.businessRules.createClass;

import application.entities.ClassRoom;
import application.services.exceptions.classRoomService.MaximumOfClassRoomsException;

import java.util.List;

public class MaximumOfClassRooms implements CreateClassCheck {

    @Override
    public void validation(List<ClassRoom> classRooms) {

        int size = classRooms.size();

        if (size >= 19) {
            throw new MaximumOfClassRoomsException("The system can't have more than 19 classrooms");
        }

    }
}

