package ru.otus.json.writer.gson;

public class GsonStringBuild {

    //Удаляем запятиую из конца строки.
    private static StringBuilder checkLastChar(StringBuilder sb) {
        if (sb.length() == 0) return sb;
        if (sb.lastIndexOf(",") == sb.length()-1) {
            int lastIdx = sb.lastIndexOf(",");
            sb.delete(lastIdx, sb.length());
        }
        return sb;
    }

    //Оборачиваем строку скобками строки из массива.
    public static StringBuilder arrayWraper(StringBuilder sb) {
        checkLastChar(sb);
        sb.insert(0, "[");
        sb.append("]");
        sb.append(",");
        return sb;
    }

    //Устанавливаем разделитель для имени поля.
    public static StringBuilder stringFieldWraper(StringBuilder sb, Object object) {
        sb.append(stringBuild(object));
        sb.append(":");
        return sb;
    }

    //Устанавливаем разделитель для объекта.
    public static StringBuilder objectStringWraper(StringBuilder sb, Object object) {
        sb.append(stringBuild(object));
        sb.append(",");
        return sb;
    }

    //Оборачиваем ркзультат в фигурные скобки.
    public static StringBuilder resultStringWraper(StringBuilder sb) {
        checkLastChar(sb);
        sb.insert(0, "{");
        sb.append("}");
        return sb;
    }

    //Строим строку.
    public static StringBuilder stringBuild(Object object) {
        var  sb = new StringBuilder();
        sb.append("\"");
        for(int i = 0; i < object.toString().length(); i++) {
            char c = object.toString().charAt(i);
            if (c >= 0x20 && c != '\"' && c != '\\' ) {
                sb.append(c);
            } else {
                switch (c) {
                    case '\\':
                        sb.append('\\');
                        sb.append(c);
                        break;
                    case '\b':
                        sb.append('\\');
                        sb.append('b');
                        break;
                    case '\f':
                        sb.append('\\');
                        sb.append('f');
                        break;
                    case '\n':
                        sb.append('\\');
                        sb.append('n');
                        break;
                    case '\r':
                        sb.append('\\');
                        sb.append('r');
                        break;
                    case '\t':
                        sb.append('\\');
                        sb.append('t');
                        break;
                    default:
                        String hex = "000" + Integer.toHexString(c);
                        sb.append("\\u").append(hex.substring(hex.length() - 4));
                }
            }
        }
        sb.append("\"");
        return sb;
    }

    //Записываем объект в строку.
    public static StringBuilder objectStringBuild(Object object) {
        var  sb = new StringBuilder();
        sb.append(object.toString());
        sb.append(",");
        return sb;
    }
}
