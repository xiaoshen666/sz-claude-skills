package com.supcon.nebule.aiprocts.controller;

import com.supcon.nebule.fr.service.StatisticsService;
import com.supcon.nebule.fr.vo.StatisticsDimensionAndIndicatorParam;
import com.supcon.nebule.fr.vo.StatisticsDimensionAndIndicatorResp;
import com.supcon.supfusion.framework.cloud.annotation.InternalApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@InternalApi(path = HttpConstants.URL_SPLITER + "aiprocts" + HttpConstants.URL_SPLITER + "statistics")
@Slf4j
@Api(value = "统计页面", tags = {"统计页面"})
public class AiproctsStatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @PostMapping("/dimension-and-indicator")
    @ApiOperation("根据维度和指标统计（柱状图、折线图、饼状图）")
    public Result<List<StatisticsDimensionAndIndicatorResp>> dimensionAndIndicator(@RequestBody @Valid StatisticsDimensionAndIndicatorParam param) {
        List<StatisticsDimensionAndIndicatorResp> list = statisticsService.getStatisticsByDimensionAndIndicator(param);
        return Result.success(list);
    }
}

