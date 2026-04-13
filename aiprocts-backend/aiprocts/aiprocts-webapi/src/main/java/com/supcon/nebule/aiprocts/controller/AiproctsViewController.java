package com.supcon.nebule.aiprocts.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import com.supcon.nebule.fr.vo.RtViewMetadataVO;
import com.supcon.nebule.fr.service.RtViewMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.supcon.nebule.fr.pubservice.FileDocumentService;
import com.supcon.nebule.fr.entity.Document;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.IOException;

@Controller
public class AiproctsViewController{

    @Autowired
    private RtViewMetadataService rtViewMetadataService;
    private final static String[] agent = {"Android","iPhone","iPod","iPad","Windows Phone","supMobile"};

    @Autowired
    private FileDocumentService fileDocumentService;
    /**
    * 页面统一入口
     *  viewCode:kong    url:/msService/aiprocts/page-view/kong
     */
    @RequestMapping(value = "/aiprocts/page-view/{viewCode}")
    public String view(Model model,@PathVariable("viewCode") String viewCode,@RequestParam(value = "clientType",required = false) String clientType, HttpServletRequest request){
        model.addAttribute("url","/aiprocts/page-view");
        model.addAttribute("moduleCode","aiprocts");
        boolean flag  = false;
        String userAgent = request.getHeader("User-Agent");
        for(String item:agent){
            if(!StringUtils.isEmpty(userAgent) && userAgent.contains(item)){
                flag =true;
                break;
            }
        }
         if((clientType != null && clientType.equals("mobile"))||flag){
         Long viewId = rtViewMetadataService.getIdbyCode("aiprocts_"+viewCode,"2");
          if(null == viewId){
              return "aiproctsView";
          }
            return "aiproctsView-h5";
        }
        return "aiproctsView";
    }


    @RequestMapping(value = "/aiprocts/proj/page-view/{viewCode}")
    public String projView(Model model,@PathVariable("viewCode") String viewCode,@RequestParam(value = "clientType",required = false) String clientType, HttpServletRequest request){
        model.addAttribute("url","/aiprocts/proj/page-view");
        model.addAttribute("moduleCode","aiprocts");
        boolean flag  = false;
        String userAgent = request.getHeader("User-Agent");
        for(String item:agent){
            if(!StringUtils.isEmpty(userAgent) && userAgent.contains(item)){
                flag =true;
                break;
            }
        }
         if((clientType != null && clientType.equals("mobile"))||flag){
            Long viewId = rtViewMetadataService.getIdbyCode("aiprocts_"+viewCode,"2");
           if(null == viewId){
               return "aiproctsView";
           }
             return "aiproctsView-h5";
         }
        return "aiproctsView";
    }


    /**
     * 根据code获取视图VO
     * @param code 视图编码
     * @return 视图VO
     */
    @GetMapping("/aiprocts/get-by-code")
    @ResponseBody
    public Result<RtViewMetadataVO> getByCode(@RequestParam("code") String code,@RequestParam(value = "clientType",required = false) String clientType, HttpServletRequest request) {
            boolean flag  = false;
            String userAgent = request.getHeader("User-Agent");
            for(String item:agent){
                if(!StringUtils.isEmpty(userAgent) && userAgent.contains(item)){
                    flag =true;
                    break;
                }
            }
            if(clientType != null && clientType.equals("mobile")||flag){
                flag =true;
            }
            return Result.success(rtViewMetadataService.getViewMetaDataByCode(code,flag));
    }

    @GetMapping("/aiprocts/dev-version")
    @ResponseBody
    public Result<String> devVersion() {
       try {
            return  Result.success(new String(Files.readAllBytes(Paths.get("/home/local/CDS_VERSION")), StandardCharsets.UTF_8));
       } catch (IOException e) {
            return  Result.success("");
       }
    }


    @GetMapping("/aiprocts/previewFiles")
    @ResponseBody
    public Result<List<Document>> previewFiles(@RequestParam(value = "linkId") String linkId,
                                                   @RequestParam(value = "propertyCode") String propertyCode) {
        List<Document> list = fileDocumentService.previewFiles(linkId,propertyCode);
        return Result.success(list);
    }

}