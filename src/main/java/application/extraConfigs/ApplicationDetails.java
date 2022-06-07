package application.extraConfigs;

public class ApplicationDetails {

    public static String text() {
        StringBuilder bd = new StringBuilder();
        bd.append("\n");
        for (int i = 0; i < 70; i++) {
            bd.append("=");
        }
        bd.append("\n");
        bd.append("SCHOOL SYSTEM APPLICATION    ---    VICTOR ALMADA\n");
        for (int i = 0; i < 70; i++) {
            bd.append("=");
        }
        bd.append("\n");
        bd.append("*Java Version - 17 \n");
        bd.append("*Spring Boot Version -  2.6.7  \n");
        bd.append("*Dependencies : \n");
        bd.append("\n");
        bd.append("\n");
        System.out.println("\n");
        for (int i = 0; i < 70; i++) {
            bd.append("=");
        }
        bd.append("\n");
        return bd.toString();
    }
}
