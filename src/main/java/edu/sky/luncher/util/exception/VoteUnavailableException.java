package edu.sky.luncher.util.exception;

import javax.validation.constraints.NotNull;

public class VoteUnavailableException extends RuntimeException {
    public VoteUnavailableException(@NotNull String msg) {
        super(msg);
    }
}
