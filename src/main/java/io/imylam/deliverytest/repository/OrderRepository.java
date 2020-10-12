package io.imylam.deliverytest.repository;

import io.imylam.deliverytest.model.Order;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Order findById(int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE orders SET status=?2 WHERE id=?1 AND status=?3")
    int updateStatus(int id, String status, String unassignedStatus);

    @Query(nativeQuery = true, value ="SELECT * FROM orders LIMIT ?1 OFFSET ?2")
    List<Order> findByPage(int limit, int page);
}
