package com.mioto.pms.module.rental.dao;

import com.mioto.pms.module.rental.model.RentalFile;
import com.mioto.pms.module.rental.model.RentalFileVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @date 2021-07-08 15:49
 */
@Repository
public interface RentalFileDao {

    int insertRentalFile(@Param("rentalId") String rentalId,@Param("list") List<RentalFile> rentalFileList);

    int delByRentalId(String rentalId);

    List<RentalFileVO> findRentalFileListByRentalId(String rentalId);
}
