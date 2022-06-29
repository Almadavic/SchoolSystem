package application.service.businessRule.createClass;

import application.entity.ClassRoom;

import java.util.List;

public interface CreateClassCheck {

    void validation(List<ClassRoom> classRooms);
}
