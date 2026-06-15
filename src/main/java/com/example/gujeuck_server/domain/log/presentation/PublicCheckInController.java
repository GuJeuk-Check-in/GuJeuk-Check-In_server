package com.example.gujeuck_server.domain.log.presentation;

import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.OrganResponse;
import com.example.gujeuck_server.domain.organ.service.QueryOrganNameListService;
import com.example.gujeuck_server.domain.purpose.presentation.dto.response.PurposeResponse;
import com.example.gujeuck_server.domain.purpose.service.QueryPurposeListService;
import com.example.gujeuck_server.domain.residence.presentation.dto.response.ResidenceResponse;
import com.example.gujeuck_server.domain.residence.service.QueryResidenceListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicCheckInController {
    private final QueryOrganNameListService queryOrganNameListService;
    private final QueryPurposeListService queryPurposeListService;
    private final QueryResidenceListService queryResidenceListService;
    private final OrganFacade organFacade;

    @GetMapping("/organs")
    public List<OrganResponse> queryOrganNameList() {
        return queryOrganNameListService.execute();
    }

    @GetMapping("/organs/{organName}/purposes")
    public List<PurposeResponse> queryPurposeList(@PathVariable String organName) {
        return queryPurposeListService.execute(organFacade.getOrganByName(organName).getId());
    }

    @GetMapping("/organs/{organName}/residences")
    public List<ResidenceResponse> queryResidenceList(@PathVariable String organName) {
        return queryResidenceListService.execute(organFacade.getOrganByName(organName).getId());
    }
}
