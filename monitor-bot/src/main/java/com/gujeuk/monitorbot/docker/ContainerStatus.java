package com.gujeuk.monitorbot.docker;

public record ContainerStatus(
        String containerName,
        ContainerState state,
        HealthState health,
        /* 도커가 이 컨테이너를 재시작한 누적 횟수. 짧은 시간에 늘어나면 크래시 루프 신호다. */
        int restartCount,
        /* 마지막 종료가 OOM 이었는지. 메모리 부족을 재시작과 구분하기 위해 따로 본다. */
        boolean oomKilled
) {
}
