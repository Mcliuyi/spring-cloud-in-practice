package net.jaggerwang.scip.gateway.adapter.graphql;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.ExecutionPath;
import graphql.language.SourceLocation;
import net.jaggerwang.scip.common.usecase.exception.NotFoundException;
import net.jaggerwang.scip.common.usecase.exception.UnauthenticatedException;
import net.jaggerwang.scip.common.usecase.exception.UnauthorizedException;
import net.jaggerwang.scip.common.usecase.exception.UsecaseException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomDataFetchingExceptionHandler implements DataFetcherExceptionHandler {
    public static class CustomDataFetchingError implements GraphQLError {
        private ExecutionPath path;
        private Throwable throwable;
        private SourceLocation location;

        public CustomDataFetchingError(ExecutionPath path, Throwable throwable, SourceLocation location) {
            this.path = path;
            this.throwable = throwable;
            this.location = location;
        }

        @Override
        public String getMessage() {
            return throwable instanceof UsecaseException ?
                    throwable.getMessage() : throwable.toString();
        }

        @Override
        public List<SourceLocation> getLocations() {
            return Collections.singletonList(location);
        }

        @Override
        public ErrorType getErrorType() {
            return ErrorType.DataFetchingException;
        }

        @Override
        public List<Object> getPath() {
            return path.toList();
        }

        @Override
        public Map<String, Object> getExtensions() {
            var extensions = new LinkedHashMap<String, Object>();
            var code = "fail";
            if (throwable instanceof NotFoundException) {
                code = "not_found";
            } else if (throwable instanceof UnauthenticatedException) {
                code = "unauthenticated";
            } else if (throwable instanceof UnauthorizedException ||
                    throwable instanceof AccessDeniedException) {
                code = "unauthorized";
            }
            extensions.put("code", code);

            if (throwable instanceof GraphQLError) {
                var map = ((GraphQLError) throwable).getExtensions();
                if (map != null) extensions.putAll(map);
            }

            return extensions;
        }
    }

    @Override
    public void accept(DataFetcherExceptionHandlerParameters handlerParameters) {
        var path = handlerParameters.getPath();
        var exception = handlerParameters.getException();
        var location = handlerParameters.getField().getSourceLocation();
        var error = new CustomDataFetchingError(path, exception, location);
        handlerParameters.getExecutionContext().addError(error);
    }
}
