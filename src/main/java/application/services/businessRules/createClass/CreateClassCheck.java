package application.services.businessRules.createClass;

import application.entities.ClassRoom;

import java.util.List;

public interface CreateClassCheck {

    void validation(List<ClassRoom> classRooms);
}
