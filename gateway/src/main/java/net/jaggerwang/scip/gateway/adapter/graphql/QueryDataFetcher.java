package net.jaggerwang.scip.gateway.adapter.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.scip.gateway.api.security.annotation.PermitAll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class QueryDataFetcher extends AbstractDataFetcher {
    public DataFetcher authLogout() {
        return new DataFetcher() {
            @PermitAll
            @Override
            public Object get(DataFetchingEnvironment env) {
                return monoWithContext(logoutUser()
                        .flatMap(loggedUser -> userAsyncService.info(loggedUser.getId())), env);
            }
        };
    }

    public DataFetcher authLogged() {
        return new DataFetcher() {
            @PermitAll
            @Override
            public Object get(DataFetchingEnvironment env) {
                return monoWithContext(loggedUserId()
                        .flatMap(userId -> userAsyncService.info(userId)), env);
            }
        };
    }

    @PreAuthorize("hasRole('ADMIN')")
    public DataFetcher userInfo() {
        return env -> {
            var id = Long.valueOf((Integer) env.getArgument("id"));
            return monoWithContext(userAsyncService.info(id), env);
        };
    }

    public DataFetcher userFollowing() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            var limit = env.getArgument("limit") != null ?
                    Long.valueOf((Integer) env.getArgument("limit")) : null;
            var offset = env.getArgument("offset") != null ?
                    Long.valueOf((Integer) env.getArgument("offset")) : null;
            return monoWithContext(userAsyncService.following(userId, limit, offset), env);
        };
    }

    public DataFetcher userFollowingCount() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            return monoWithContext(userAsyncService.followingCount(userId), env);
        };
    }

    public DataFetcher userFollower() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            var limit = env.getArgument("limit") != null ?
                    Long.valueOf((Integer) env.getArgument("limit")) : null;
            var offset = env.getArgument("offset") != null ?
                    Long.valueOf((Integer) env.getArgument("offset")) : null;
            return monoWithContext(userAsyncService.follower(userId, limit, offset), env);
        };
    }

    public DataFetcher userFollowerCount() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            return monoWithContext(userAsyncService.followerCount(userId), env);
        };
    }

    public DataFetcher postInfo() {
        return env -> {
            var id = Long.valueOf((Integer) env.getArgument("id"));
            return monoWithContext(postAsyncService.info(id), env);
        };
    }

    public DataFetcher postPublished() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            var limit = env.getArgument("limit") != null ?
                    Long.valueOf((Integer) env.getArgument("limit")) : null;
            var offset = env.getArgument("offset") != null ?
                    Long.valueOf((Integer) env.getArgument("offset")) : null;
            return monoWithContext(postAsyncService.published(userId, limit, offset), env);
        };
    }

    public DataFetcher postPublishedCount() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            return monoWithContext(postAsyncService.publishedCount(userId), env);
        };
    }

    public DataFetcher postLiked() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            var limit = env.getArgument("limit") != null ?
                    Long.valueOf((Integer) env.getArgument("limit")) : null;
            var offset = env.getArgument("offset") != null ?
                    Long.valueOf((Integer) env.getArgument("offset")) : null;
            return monoWithContext(postAsyncService.liked(userId, limit, offset), env);
        };
    }

    public DataFetcher postLikedCount() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            return monoWithContext(postAsyncService.likedCount(userId), env);
        };
    }

    public DataFetcher postFollowing() {
        return env -> {
            var limit = env.getArgument("limit") != null ?
                    Long.valueOf((Integer) env.getArgument("limit")) : null;
            var beforeId = env.getArgument("beforeId") != null ?
                    Long.valueOf((Integer) env.getArgument("beforeId")) : null;
            var afterId = env.getArgument("afterId") != null ?
                    Long.valueOf((Integer) env.getArgument("afterId")) : null;
            return monoWithContext(postAsyncService.following(limit, beforeId, afterId), env);
        };
    }

    public DataFetcher postFollowingCount() {
        return env -> monoWithContext(postAsyncService.followingCount(), env);
    }

    public DataFetcher fileInfo() {
        return env -> {
            var id = Long.valueOf((Integer) env.getArgument("id"));
            return monoWithContext(fileAsyncService.info(id), env);
        };
    }
}
