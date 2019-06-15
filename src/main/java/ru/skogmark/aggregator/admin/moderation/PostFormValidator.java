package ru.skogmark.aggregator.admin.moderation;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PostFormValidator {
    private static final int MAX_TITLE_LENGTH = 128;
    private static final int MAX_TEXT_LENGTH = 255;

    Optional<ValidationError> validateForm(PostForm form) {
        if (form.getPublish() != null && !form.isPublish()) {
            return Optional.of(ValidationError.INVALID_PUBLISH_VALUE);
        }
        if (form.getTitle() != null && form.getTitle().length() > MAX_TITLE_LENGTH) {
            return Optional.of(ValidationError.MAX_TITLE_LENGTH_EXCEEDED);
        }
        if (form.getText() != null && form.getText().length() > MAX_TEXT_LENGTH) {
            return Optional.of(ValidationError.MAX_TEXT_LENGTH_EXCEEDED);
        }
        if ((form.getText() == null || form.getText().isBlank()) && (form.getImageSizes().isEmpty())) {
            return Optional.of(ValidationError.EITHER_TEXT_OR_IMAGE_MUST_BE_SET);
        }
        if (form.getChannelId() == null) {
            return Optional.of(ValidationError.CHANNEL_ID_MUST_BE_SET);
        } else {
            try {
                Integer.parseInt(form.getChannelId());
            } catch (NumberFormatException e) {
                return Optional.of(ValidationError.CHANNEL_ID_IS_NOT_A_NUMBER);
            }
        }
        return Optional.empty();
    }

    enum ValidationError implements ErrorCode {
        INVALID_PUBLISH_VALUE("Некорректное значение поля 'Опубликовать'"),
        MAX_TITLE_LENGTH_EXCEEDED("Превышено максимальное значение поля 'Заголовок'"),
        MAX_TEXT_LENGTH_EXCEEDED("Превышено максимальное значение поля 'Текст'"),
        EITHER_TEXT_OR_IMAGE_MUST_BE_SET("Должны быть заданы значения для полей 'Текст' либо 'Изображение'"),
        CHANNEL_ID_MUST_BE_SET("Должно быть задано поле 'Канал'"),
        CHANNEL_ID_IS_NOT_A_NUMBER("Поле 'Канал' не является числом");

        private final String description;

        ValidationError(String description) {
            this.description = description;
        }

        @Override
        public String getCode() {
            return name();
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
