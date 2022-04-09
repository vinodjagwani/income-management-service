package au.seisma.income.exception;

import org.springframework.http.HttpStatus;

public interface ErrorPrinter {

    HttpStatus getHttpStatus();

}
