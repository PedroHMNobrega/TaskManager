package domain.exceptions;

public class CommandErrorException extends Exception
{
    public CommandErrorException() {
        super();
    }

    public CommandErrorException(String message) {
        super(message);
    }
}
