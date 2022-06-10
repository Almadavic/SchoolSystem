package application.extraConfigs;

public class ApplicationDetails {

    public static String text() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(forFormatting());
        sb.append("\n");
        sb.append("SCHOOL SYSTEM APPLICATION    ---    VICTOR ALMADA\n");
        sb.append(forFormatting());
        sb.append("\n");
        sb.append("*Java Version - 17 \n");
        sb.append("*Spring Boot Version -  2.6.7  \n");                     // COLOCAR QUANTAS LINHAS DE CÓDIGO E DEPENDENCIAS!
        sb.append("*Dependencies : \n");
        sb.append("*Number of lines : \n");
        sb.append("*Number of Classes / Files : \n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append(forFormatting());
        sb.append("\n");
        return sb.toString();
    }

    private static String forFormatting() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 70; i++) {
            sb.append("=");
        }
        return sb.toString();
    }
}
