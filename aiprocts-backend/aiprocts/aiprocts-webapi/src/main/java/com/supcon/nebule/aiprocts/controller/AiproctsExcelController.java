package com.supcon.nebule.aiprocts.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.supcon.nebule.excel.bo.SysImportRecordsBO;
import com.supcon.nebule.excel.vo.SysImportRecordsVO;
import com.supcon.nebule.framework.common.entity.PageQuery;
import com.supcon.nebule.excel.service.SysImportRecordsService;
import com.supcon.nebule.excel.dto.ResultDTO;
import com.supcon.nebule.excel.pojo.LogInfo;
import com.supcon.nebule.excel.bo.ExportTaskBO;
import com.supcon.nebule.excel.bo.NbRtPropertyBO;
import com.supcon.nebule.excel.vo.NbRtPropertyVO;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import com.supcon.nebule.excel.service.ExcelExportService;
import com.supcon.nebule.excel.service.ExcelImportService;
import com.supcon.nebule.excel.service.ExcelLogService;
import com.supcon.nebule.excel.service.ExcelTemplateService;
import com.supcon.nebule.excel.service.NbRtPropertyService;
import com.supcon.nebule.excel.vo.ExportTaskVO;
import com.supcon.nebule.framework.common.entity.Page;
import com.supcon.nebule.framework.common.utils.PojoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class AiproctsExcelController {
    @Autowired
    private ExcelImportService excelImportService;

    @Autowired
    private ExcelExportService excelExportService;

    @Autowired
    private ExcelLogService excelLogService;
    @Autowired
    private ExcelTemplateService excelTemplateService;
    @Autowired
    private NbRtPropertyService nbRtPropertyService;
    @Autowired
    private SysImportRecordsService sysImportRecordsService;

    @PostMapping(path = "/aiprocts/export/download")
    public ResponseEntity download(@RequestParam(value = "taskId") Long taskId, @RequestParam(value = "fileName") String fileName) throws IOException {
        return excelExportService.download(taskId,fileName);
    }

    @GetMapping("/aiprocts/excel/log")
    public LogInfo logInfo(@RequestParam(value = "taskId") Long taskId,
                           @RequestParam(value = "line", required = false) Integer line) {
        LogInfo logInfo = excelLogService.readLogInfo(taskId, line);
        return logInfo;
    }

    @GetMapping("/aiprocts/excel/backup/download")
    public ResponseEntity templateDownload(@RequestParam("taskId") String taskId,
                                               @RequestParam("moduleCode")String moduleCode) throws IOException  {
           return excelImportService.downLoad(taskId,moduleCode);
    }

    @PostMapping(path = "/aiprocts/excel/import", consumes = "multipart/form-data")
    public Result<Long> importTask(@RequestPart("file") MultipartFile file,
         @RequestParam(value = "task",required = false) String taskJson) throws IOException {
        return Result.success(excelImportService.taskImport(taskJson,file.getOriginalFilename(), file.getInputStream()));
    }


    @GetMapping("/aiprocts/excel/template/download")
    public void templateDownload(HttpServletResponse response, @RequestParam("code") String code,
                                    @RequestParam("moduleCode") String moduleCode,
                     @RequestParam("fileName") String fileName) throws IOException  {
        excelTemplateService.downLoad(response,code,moduleCode,fileName);
    }

    @GetMapping("/aiprocts/excel/property/query")
    public Result<List<NbRtPropertyVO>> queryProperties(@RequestParam("modelCode") String modelCode
                                                      )  {
        List<NbRtPropertyBO> nbRtPropertyBOS = nbRtPropertyService.queryByModelCode(modelCode);
        return Result.success(PojoUtil.copyList(nbRtPropertyBOS, NbRtPropertyVO.class));
    }

    @PostMapping("/aiprocts/excel/import/page-records")
    public Result<IPage<SysImportRecordsVO>> pageRecords(@RequestBody PageQuery<SysImportRecordsVO> pageQuery){
        IPage<SysImportRecordsBO> sysImportRecordsBOIPage = sysImportRecordsService.pageRecord(pageQuery);
        IPage<SysImportRecordsVO> convert = sysImportRecordsBOIPage.convert(result -> {
            SysImportRecordsVO copy = PojoUtil.copy(result, SysImportRecordsVO.class);
            return copy;
        });
        return Result.success(convert);
    }
}