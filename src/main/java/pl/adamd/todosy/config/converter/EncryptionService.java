package pl.adamd.todosy.config.converter;

public interface EncryptionService {

    String encrypt(String value);
    String decrypt(String encrypted);
}
