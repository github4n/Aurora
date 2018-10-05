package me.aurora.app.rest.system;

import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.domain.Dict;
import me.aurora.domain.ResponseEntity;
import me.aurora.repository.spec.DictSpce;
import me.aurora.service.DictService;
import me.aurora.util.HttpContextUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/10/05 12:11:05
 */
@Slf4j
@RestController
@RequestMapping("dict")
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 跳转到列表页面
     * @return
     */
    @GetMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("/system/dict/index");
    }

    @Log("查询所有字典")
    @RequiresPermissions(value={"admin", "dict:all","dict:select"}, logical= Logical.OR)
    @GetMapping(value = "/getDictsInfo")
    public Map getDictsInfo(@RequestParam(value = "tableName",required = false) String tableName,
                            @RequestParam(value = "fieldName",required = false) String fieldName,
                            @RequestParam(value = "fieldValue",required = false) String fieldValue,
                            @RequestParam(value = "fieldDetail",required = false) String fieldDetail,
                            @RequestParam(value = "page",defaultValue = "1")Integer page,
                            @RequestParam(value = "limit",defaultValue = "10")Integer limit){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(page-1,limit,sort);
        return dictService.getDictInfo(new DictSpce(tableName,fieldName,fieldValue,fieldDetail),pageable);
    }

    /**
     * 去新增页面
     * @return
     */
    @RequiresPermissions (value={"admin", "dict:all","dict:add"}, logical= Logical.OR)
    @GetMapping(value = "/toAddPage")
    public ModelAndView toAddPage(){
        return new ModelAndView("/system/dict/add");
    }

    /**
     * 新增
     * @param dict
     * @return
     */
    @Log("新增字典")
    @RequiresPermissions (value={"admin", "dict:all","dict:add"}, logical= Logical.OR)
    @PostMapping(value = "/insert")
    public ResponseEntity insert(@Validated(Dict.New.class) @RequestBody Dict dict){
        log.warn("REST request to insert Dict : {}"+dict);
        dictService.insert(dict);
        return ResponseEntity.ok();
    }

    /**
     * 去编辑页面
     * @return
     */
    @RequiresPermissions (value={"admin", "dict:all","dict:update"}, logical= Logical.OR)
    @GetMapping(value = "/toUpdatePage")
    public ModelAndView toUpdatePage(@RequestParam Long id){
        Dict dict = dictService.findById(id);
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        request.setAttribute("dict",dict);
        return new ModelAndView("/system/dict/update");
    }

    /**
     * 更新
     * @param dict
     * @return
     */
    @Log("更新字典")
    @RequiresPermissions (value={"admin", "dict:all","dict:update"}, logical= Logical.OR)
    @PutMapping(value = "/update")
    public ResponseEntity update(@Validated(Dict.Update.class) @RequestBody Dict dict) {
        log.warn("REST request to update Dict : {}" +dict);
        dictService.update(dictService.findById(dict.getId()),dict);
        return ResponseEntity.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Log("删除字典")
    @RequiresPermissions (value={"admin", "dict:all","dict:delete"}, logical= Logical.OR)
    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@RequestParam Long id){
        log.warn("REST request to delete Dict : {}" + id);
        dictService.delete(dictService.findById(id));
        return ResponseEntity.ok();
    }

}
