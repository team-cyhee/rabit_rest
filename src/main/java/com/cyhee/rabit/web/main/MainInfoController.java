package com.cyhee.rabit.web.main;

import com.cyhee.rabit.model.main.MainInfo;
import com.cyhee.rabit.service.main.MainService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("rest/v1/maininfos")
public class MainInfoController {
    @Resource(name="mainService")
    private MainService mainService;

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<MainInfo>> getMainInfos(@PageableDefault Pageable pageable) {
        List<MainInfo> mainInfoList = mainService.getMainInfos(pageable);
        return new ResponseEntity<>(mainInfoList, HttpStatus.OK);
    }
}


