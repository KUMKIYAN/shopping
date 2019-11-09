package com.myproduct.lineitem;

import com.myproduct.orders.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemRepository extends JpaRepository <LineItem, Integer> {
}