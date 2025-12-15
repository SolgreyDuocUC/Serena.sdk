package psytobetter.emotion.mcsv_emotion.exception;

public class EmotionNotFoundException extends RuntimeException {

    public EmotionNotFoundException(Long id) {
        super("Emotion con ID " + id + " no encontrada.");
    }
}
