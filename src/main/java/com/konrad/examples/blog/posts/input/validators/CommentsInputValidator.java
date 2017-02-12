package com.konrad.examples.blog.posts.input.validators;

import com.konrad.examples.blog.comments.domain.Comment;
import com.konrad.examples.blog.posts.input.validators.impl.InputFilter;
import com.konrad.examples.blog.posts.input.validators.impl.JsonEscaperFilter;
import com.konrad.examples.blog.posts.input.validators.impl.LengthValidator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentsInputValidator {

    public Validator[] validators = new Validator[3];

    public CommentsInputValidator() {
        validators[0] = new LengthValidator(1500);
        validators[1] = new InputFilter();
        validators[2] = new JsonEscaperFilter();
    }

    public Comment validate(Comment input) {
        input.setName(validateField(input.getName()));
        input.setPostId(validateField(input.getPostId()));
        input.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").format(new Date()));
        input.setText(validateField(input.getText()));
        input.setBrowserData("");
        input.setEmail(validateField(input.getEmail()));
        input.setIp("");
        checkEmailAndNameForIgla(input);
        return input;
    }

    private void checkEmailAndNameForIgla(Comment comment) {
        if (comment.getName().contains("igla") || comment.getName().contains("podrozy") || comment.getName().contains("igła") || comment.getName().contains("podroży")) {
            if (comment.getEmail().contains("martaikonrad@martaikonrad.com")) {
                return;
            }
        }
        throw new RuntimeException("Cheater!");
    }

    public String validateField(String input) {
        for (Validator<String> validator : validators) {
            input = validator.validate(input);
        }
        return input;
    }
}
