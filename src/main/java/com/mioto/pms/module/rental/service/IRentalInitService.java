package com.mioto.pms.module.rental.service;

import com.mioto.pms.module.rental.model.RentalInit;

import java.util.List;

/**
 * @author admin
 * @date 2021-08-14 14:47
 */
public interface IRentalInitService {

    int insert(List<RentalInit> rentalInitList,String rentalId);

    int update(List<RentalInit> rentalInitList,String rentalId);

    Double findInitVal(String costType,String rentalId);
}
