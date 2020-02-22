package net.jaggerwang.scip.gateway.adapter.graphql;

import graphql.schema.DataFetcher;
import net.jaggerwang.scip.common.usecase.port.service.dto.PostDto;
import net.jaggerwang.scip.common.usecase.port.service.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class MutationDataFetchers extends AbstractDataFetchers {
    public DataFetcher userRegister() {
        return env -> {
            UserDto userInput = env.getArgument("userInput");
            return userAsyncService.register(userInput).toFuture();
        };
    }

    public DataFetcher userModify() {
        return env -> {
            UserDto userInput = env.getArgument("userInput");
            String code = env.getArgument("code");
            return userAsyncService.modify(userInput, code).toFuture();
        };
    }

    public DataFetcher userSendMobileVerifyCode() {
        return env -> {
            String type = env.getArgument("type");
            String mobile = env.getArgument("mobile");
            return userAsyncService.sendMobileVerifyCode(type, mobile).toFuture();
        };
    }

    public DataFetcher userFollow() {
        return env -> {
            var userId = Long.valueOf((Integer) env.getArgument("userId"));
            return userAsyncService.follow(userId).toFuture();
        };
    }

    public DataFetcher userUnfollow() {
        return env -> {
            var userId = Long.valueOf((Integer) env.getArgument("userId"));
            return userAsyncService.unfollow(userId).toFuture();
        };
    }

    public DataFetcher postPublish() {
        return env -> {
            PostDto postInput = env.getArgument("postInput");
            return postAsyncService.publish(postInput).toFuture();
        };
    }

    public DataFetcher postDelete() {
        return env -> {
            var id = Long.valueOf((Integer) env.getArgument("id"));
            return postAsyncService.delete(id).toFuture();
        };
    }

    public DataFetcher postLike() {
        return env -> {
            var postId = Long.valueOf((Integer) env.getArgument("postId"));
            return postAsyncService.like(postId).toFuture();
        };
    }

    public DataFetcher postUnlike() {
        return env -> {
            var postId = Long.valueOf((Integer) env.getArgument("postId"));
            return postAsyncService.unlike(postId).toFuture();
        };
    }
}