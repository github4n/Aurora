package me.aurora.service.impl;

import cn.hutool.http.HttpStatus;
import me.aurora.config.exception.AuroraException;
import me.aurora.domain.Dict;
import me.aurora.repository.DictRepo;
import me.aurora.repository.spec.DictSpce;
import me.aurora.service.DictService;
import me.aurora.util.PageUtil;
import me.aurora.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

/**
 * @author 郑杰
 * @date 2018/10/05 12:09:28
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictRepo dictRepo;

    @Override
    public Map getDictInfo(DictSpce dictSpce, Pageable pageable) {
        Page<Dict> dicts = dictRepo.findAll(dictSpce,pageable);
        return PageUtil.buildPage(dicts.getContent(),dicts.getTotalElements());
    }

    @Override
    public void insert(Dict dict) {
        dictRepo.save(dict);
    }

    @Override
    public Dict findById(Long id) {
        if(id == null){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"id not exist");
        }
        Optional<Dict> dict = dictRepo.findById(id);
        ValidationUtil.isNull(dict,"id:"+id+" is not find");
        return dict.get();
    }

    @Override
    public void update(Dict old, Dict dict) {
        dict.setCreateTime(old.getCreateTime());
        dictRepo.save(dict);
    }

    @Override
    public void delete(Dict dict) {
        dictRepo.delete(dict);
    }
}
