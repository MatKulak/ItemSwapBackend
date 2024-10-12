package pl.mateusz.swap_items_backend.others;

public final class Messages {
    private Messages() {
        // Prywatny konstruktor, aby zapobiec instancjonowaniu
    }

    public static final String NAME_MANDATORY = "Name is mandatory";
    public static final String NAME_MIN_LENGTH = "Name should have at least 2 characters";
    public static final String SURNAME_MANDATORY = "Surname is mandatory";
    public static final String SURNAME_MIN_LENGTH = "Surname should have at least 2 characters";
    public static final String EMAIL_MANDATORY = "Email is mandatory";
    public static final String EMAIL_INVALID = "Email should be valid";
    public static final String USERNAME_MANDATORY = "Username is mandatory";
    public static final String USERNAME_MIN_LENGTH = "Username should have at least 4 characters";
    public static final String PASSWORD_MANDATORY = "Password is mandatory";
    public static final String PASSWORD_MIN_LENGTH = "Password should have at least 6 characters";

    //EXCEPTIONS

    public static final String NON_MATCHING_PASSWORDS = "Password doesn't match confirm password";
    public static final String EMAIL_ALREADY_EXISTS = "Provided email is already used";
    public static final String PHONE_NUMBER_ALREADY_EXISTS = "Provided phone number is already used";
    public static final String USERNAME_ALREADY_EXISTS = "Provided username is already used";
    public static final String ENTITY_NOT_FOUND = "Entity not found";
    public static final String NOT_ALLOWED_EXTENSION = "Invalid file type";


}
