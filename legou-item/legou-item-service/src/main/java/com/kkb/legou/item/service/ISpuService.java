package com.kkb.legou.item.service;

import com.kkb.legou.core.service.ICrudService;
import com.kkb.legou.item.po.Spu;

public interface ISpuService extends ICrudService<Spu> {


    public void saveSpu(Spu spu);
}
