public abstract class Algorithm {

    abstract String encrypt(String[] words, String[] key) throws Exception;

    abstract String decrypt(String[] words, String[] key) throws Exception;
}