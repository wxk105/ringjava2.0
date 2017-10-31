package com.jumu.ring.dao;

import com.jumu.ring.entity.CrbtOrder;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Administrator on 2017/8/22.
 */
public interface OrderDao extends CrudRepository<CrbtOrder,Long> {

}
