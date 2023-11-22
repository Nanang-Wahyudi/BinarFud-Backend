package app.BinarFudBackend.repository;

import app.BinarFudBackend.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {

    @Query(nativeQuery = true,
           value = "SELECT *\n " +
                   "FROM orders\n " +
                   "INNER JOIN users ON orders.user_id = users.user_id\n " +
                   "WHERE users.user_name = :user_name")
    List<Orders> findOrdersByUsername(@Param("user_name") String username);
}
