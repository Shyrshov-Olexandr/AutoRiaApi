package com.example.autoriaapi.repositories;


import com.example.autoriaapi.models.CarUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarUser, Long> {
    //    Optional<Car> findByBrand(EBrand brand);
//    @Query("select c from CarUser c where c.brand=:brand")
//    List<CarUser> getByBrand(@Param("brand") String brand);
    @Query("select c from CarUser c where c.brand=:brand")
    List<CarUser> findByBrand(@Param("brand") String brand);
    @Query("select c from CarUser c where c.price=:price")
    List<CarUser> getMiddle(@Param("price")String price);
    @Query("select c from CarUser c where c.region=:region")
    List<CarUser> getByRegion(@Param("region")String region);
    @Query("select c from CarUser c where c.region=:region and c.brand=:brand")
    List<CarUser> findByRegionAndBrand(String region, String brand);



}