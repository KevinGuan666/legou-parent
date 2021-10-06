package com.kkb.legou.admin.service.impl;

import com.kkb.legou.admin.po.Post;
import com.kkb.legou.admin.service.IPostService;
import com.kkb.legou.core.service.impl.CrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends CrudServiceImpl<Post> implements IPostService {

}
