package duoc.projects.serena.exception;

public class EmotionNotFoundException extends RuntimeException {

    public EmotionNotFoundException(Long id) {
        super("Emotion con ID " + id + " no encontrada.");
    }
}
