package net.jaggerwang.scip.gateway.adapter.graphql.datafetcher.userstat;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.scip.common.usecase.port.service.dto.UserStatDto;
import net.jaggerwang.scip.gateway.adapter.graphql.datafetcher.AbstractDataFetcher;
import org.springframework.stereotype.Component;

@Component
public class UserStatUserDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        UserStatDto userStatDto = env.getSource();
        return userAsyncService.info(userStatDto.getUserId());
    }
}
