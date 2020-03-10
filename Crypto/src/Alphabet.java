import java.util.HashMap;
import java.util.Map;

public class Alphabet {
    public static Map<Character, Integer> forward = new HashMap<>();
    public static Map<Integer, Character> backward = new HashMap<>();

    static {
        forward.put('А', 0);
        forward.put('Б', 1);
        forward.put('В', 2);
        forward.put('Г', 3);
        forward.put('Д', 4);
        forward.put('Е', 5);
        forward.put('Ё', 6);
        forward.put('Ж', 7);
        forward.put('З', 8);
        forward.put('И', 9);
        forward.put('Й', 10);
        forward.put('К', 11);
        forward.put('Л', 12);
        forward.put('М', 13);
        forward.put('Н', 14);
        forward.put('О', 15);
        forward.put('П', 16);
        forward.put('Р', 17);
        forward.put('С', 18);
        forward.put('Т', 19);
        forward.put('У', 20);
        forward.put('Ф', 21);
        forward.put('Х', 22);
        forward.put('Ц', 23);
        forward.put('Ч', 24);
        forward.put('Ш', 25);
        forward.put('Щ', 26);
        forward.put('Ъ', 27);
        forward.put('Ы', 28);
        forward.put('Ь', 29);
        forward.put('Э', 30);
        forward.put('Ю', 31);
        forward.put('Я', 32);
        //forward.put(' ', 33);

        backward.put(0, 'А');
        backward.put(1, 'Б');
        backward.put(2, 'В');
        backward.put(3, 'Г');
        backward.put(4, 'Д');
        backward.put(5, 'Е');
        backward.put(6, 'Ё');
        backward.put(7, 'Ж');
        backward.put(8, 'З');
        backward.put(9, 'И');
        backward.put(10, 'Й');
        backward.put(11, 'К');
        backward.put(12, 'Л');
        backward.put(13, 'М');
        backward.put(14, 'Н');
        backward.put(15, 'О');
        backward.put(16, 'П');
        backward.put(17, 'Р');
        backward.put(18, 'С');
        backward.put(19, 'Т');
        backward.put(20, 'У');
        backward.put(21, 'Ф');
        backward.put(22, 'Х');
        backward.put(23, 'Ц');
        backward.put(24, 'Ч');
        backward.put(25, 'Ш');
        backward.put(26, 'Щ');
        backward.put(27, 'Ъ');
        backward.put(28, 'Ы');
        backward.put(29, 'Ь');
        backward.put(30, 'Э');
        backward.put(31, 'Ю');
        backward.put(32, 'Я');
        //backward.put(33, ' ');
    }
}
