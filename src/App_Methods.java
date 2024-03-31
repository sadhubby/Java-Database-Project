import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;


public class App_Methods {
    static Scanner sc = new Scanner(System.in);

    public static void if_connected() {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales?useTimezone=true&serverTimezone=UTC&user=root&password=12345");
            if (con != null) {
            	System.out.println("Connected to database");   	
            }
            else {
            	System.out.println("Failed to connect to the database...");
            }
    	con.close();
    	}catch(Exception e) {System.out.println(e.getMessage());}
    }



    public static void product_insert(){
        int stopper = 1;
        do{
        System.out.print("Enter product id: ");
        String product_code = sc.nextLine();
        System.out.print("Enter product name: ");
        String product_name = sc.nextLine();
        System.out.print("Enter product line: ");
        String product_line = sc.nextLine();
        System.out.print("Enter product scale: ");
        String product_scale = sc.nextLine();
        System.out.print("Enter product vendor: ");
        String product_vendor = sc.nextLine();
        System.out.print("Enter product description: ");
        String product_desc = sc.nextLine();
        System.out.print("Enter product quantity in stock: ");
        int product_stock = sc.nextInt();
        System.out.print("Enter product price: ");
        double product_price = sc.nextDouble();
        System.out.print("Enter product MSRP: ");
        double product_MSRP = sc.nextDouble();


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
            );
         PreparedStatement stmt;
         String q1 = "INSERT INTO products" + "(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP)"
                         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
         stmt = con.prepareStatement(q1);
         stmt.setString(1, product_code);
         stmt.setString(2, product_name);
         stmt.setString(3, product_line);
         stmt.setString(4, product_scale);
         stmt.setString(5, product_vendor);
         stmt.setString(6, product_desc);
         stmt.setInt(7, product_stock);
         stmt.setDouble(8, product_price);
         stmt.setDouble(9, product_MSRP);

         int x = stmt.executeUpdate();
         if(x > 0){
            System.out.println("\nSuccessfully inserted\n");
         }
         else{
            System.out.println("Not inserted\n");
         }
         con.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
        //    System.out.println("\nError. New record not inserted. productLine is referenced");

        }

        System.out.print("Insert another record (Y/N): ");
        char insert_loop = sc.next().charAt(0);

        if(insert_loop == 'n' || insert_loop == 'N'){
            stopper = 0;
        }
        else{
            System.out.println();
        }

    }while(stopper == 1);

}   // end of Product Insert

    public static void product_update(){
        
   
        Statement st_for_noOfCols = null;
        ResultSet rs_for_noOfCols = null;
        ResultSet rs_for_query = null;


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
            );

        String query_for_cols = "SELECT * FROM products";
        
        st_for_noOfCols = con.createStatement();
        rs_for_noOfCols = st_for_noOfCols.executeQuery(query_for_cols);

        ResultSetMetaData rsmd = (ResultSetMetaData) rs_for_noOfCols.getMetaData();
        int no_of_cols = rsmd.getColumnCount();
        
        System.out.println("Number of columns: " + no_of_cols);


        String query_test = "SELECT * FROM products WHERE productCode = ?";
        PreparedStatement ps_stmt = con.prepareStatement(query_test);
       
        System.out.print("Enter product code: ");
        String product_code = sc.next();
        ps_stmt.setString(1, product_code);
        rs_for_query = ps_stmt.executeQuery();
        boolean isTrue = true;
       
            //if statement for found record
            if(isTrue == rs_for_query.next()){
          
                //rs_for_query = ps_stmt.executeQuery();
                do{
                    String get_code = rs_for_query.getString("productCode");
                    String get_name = rs_for_query.getString("productName");
                    String get_line = rs_for_query.getString("productLine");
                    String get_scale = rs_for_query.getString("productScale");
                    String get_vendor = rs_for_query.getString("productVendor");
                    String get_desc = rs_for_query.getString("productDescription");
                    int get_quantity = rs_for_query.getInt("quantityInStock");
                    Double get_buyPrice = rs_for_query.getDouble("buyPrice");
                    Double get_MSRP = rs_for_query.getDouble("MSRP");
                   
                    System.out.println("\nproductCode:  " + get_code);
                    System.out.println("productName:  " + get_name);
                    System.out.println("productLine:  " + get_line);
                    System.out.println("productScale:  "+ get_scale);
                    System.out.println("productVendor: "+ get_vendor);
                    System.out.println("productDescription:  "+ get_desc);
                    System.out.println("quantityInStock:  "+ get_quantity);
                    System.out.println("buyPrice:  "+ get_buyPrice);
                    System.out.println("MSRP:  "+ get_MSRP);
                } while(rs_for_query.next()); 

                System.out.print("\nWant to update this record? (Y/N): ");
                char y_or_n = sc.next().charAt(0);
                if(y_or_n == 'y' || y_or_n == 'Y'){
                    //do update here
                    int update_stopper = 1;
                    do{
                        System.out.print("Enter column name to update: ");
                        String col_name = sc.next();
                        System.out.print("Enter updated value: ");
                        String new_value = sc.next();
                 
                        String update_query = "UPDATE products SET " + col_name + " = ? " + "WHERE productCode=?";
                        PreparedStatement ps_for_update = con.prepareStatement(update_query);
                        ps_for_update.setString(1, new_value);     
                        ps_for_update.setString(2, product_code);

                        int x = ps_for_update.executeUpdate(); 
                        if(x == 1){
                            System.out.println("\nRecord updated\n");
                            System.out.print("Do you still want to keep updating the record "+ product_code +" (Y/N): ");
                            char loop_update_yOrN = sc.next().charAt(0);

                            if(loop_update_yOrN == 'y' || loop_update_yOrN == 'Y'){
                                System.out.println("Continuing...\n");
                                String loop_query = "SELECT * FROM products WHERE productCode = ?";
                                PreparedStatement ps_loop = con.prepareStatement(loop_query);
                                ps_loop.setString(1, product_code);
                                ResultSet loop_output = ps_loop.executeQuery();

                                while(loop_output.next()){
                                    String get_code_loop = loop_output.getString("productCode");
                                    String get_name_loop = loop_output.getString("productName");
                                    String get_line_loop = loop_output.getString("productLine");
                                    String get_scale_loop = loop_output.getString("productScale");
                                    String get_vendor_loop = loop_output.getString("productVendor");
                                    String get_desc_loop = loop_output.getString("productDescription");
                                    int get_quantity_loop = loop_output.getInt("quantityInStock");
                                    Double get_buyPrice_loop = loop_output.getDouble("buyPrice");
                                    Double get_MSRP_loop = loop_output.getDouble("MSRP");
                                   
                                    System.out.println("productCode:  " + get_code_loop);
                                    System.out.println("productName:  " + get_name_loop);
                                    System.out.println("productLine:  " + get_line_loop);
                                    System.out.println("productScale:  "+ get_scale_loop);
                                    System.out.println("productVendor: "+ get_vendor_loop);
                                    System.out.println("productDescription:  "+ get_desc_loop);
                                    System.out.println("quantityInStock:  "+ get_quantity_loop);
                                    System.out.println("buyPrice:  "+ get_buyPrice_loop);
                                    System.out.println("MSRP:  "+ get_MSRP_loop);
                                } 
                                System.out.println();

                            }
                            else{
                                System.out.println();
                               update_stopper = 0;
                            }
                        }
                        else{
                            System.out.println("Error updating\n");
                        }
                    
                    }while(update_stopper == 1);  

                       
                }
                else{
                    System.out.println("\nNot updating...\n");
                }
            
            }    
            else{    // produces the entire record
                System.out.println("\nRecord does not exist. Going back to main menu...\n");
            }
            
            

         con.close();
        }catch(Exception e){System.out.println(e);}
            }

    public static void product_delete(){
        int stopper = 1;
        do{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
            );
            
            System.out.print("Enter column to search for value of deletion: ");
            String col_name = sc.next();
   
            System.out.print("Enter value to delete: ");
            String value = sc.next();

            String delete_query = "DELETE FROM products WHERE " + col_name + "= ?";
            PreparedStatement ps_delete = con.prepareStatement(delete_query);


            ps_delete.setString(1, value);

            int rows_affected = ps_delete.executeUpdate();

            if(rows_affected > 0){
                System.out.println("Record " + value +" deleted successfully\n");
            }

            
            else{
                System.out.println("Record does not exist or cannot be deleted\n");
            }

            System.out.println("Do you want to delete another record?(Y/N):  ");
            char delete_loop = sc.next().charAt(0);

            if(delete_loop == 'n' || delete_loop == 'N'){
                stopper = 0;
            }


            con.close();

    } catch(SQLException e){

            if(e.getErrorCode() == 1451){
                System.out.println("Error: Cannot delete record because it is being used as a reference by other records");
            }


    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    }while(stopper == 1);
}
     
    public static void product_view_orderlist(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
                );


            System.out.print("Enter product code to view: ");
            String view_code = sc.next();
           

            String view_record = "SELECT * FROM products WHERE productCode = ?";
            PreparedStatement view_record_ps = con.prepareStatement(view_record);
            view_record_ps.setString(1, view_code);

            ResultSet record_rs = view_record_ps.executeQuery();

            while(record_rs.next()){
                System.out.println("\nProduct code: " + record_rs.getString("productCode"));
                System.out.println("Product name: " + record_rs.getString("productName"));
                System.out.println("Product line: " + record_rs.getString("productLine"));
                System.out.println("Product scale: " + record_rs.getString("productScale"));
                System.out.println("Product vendor: " + record_rs.getString("productVendor"));
                System.out.println("Product desc: " + record_rs.getString("productDescription"));
                System.out.println("Product stock: " + record_rs.getString("quantityInStock"));
                System.out.println("Product price: " + record_rs.getString("buyPrice"));
                System.out.println("Product MSRP: " + record_rs.getString("MSRP"));
                System.out.println();
            }

            System.out.print("Enter year to view: ");
            String view_year = sc.next();

            String view_record_given_year = "SELECT od.orderNumber, od.quantityOrdered, od.orderLineNumber, o.orderDate, o.customerNumber FROM products p RIGHT JOIN orderdetails od ON p.productCode = od.productCode "
                                            + "RIGHT JOIN orders o ON od.orderNumber = o.orderNumber "
                                            + "WHERE p.productCode = " + "'" + view_code +"'"
                                            + "AND YEAR(orderDate) = " + view_year;

            PreparedStatement view_record_year = con.prepareStatement(view_record_given_year);

            //view_record_year.setString(1, view_code);

            ResultSet view_given_year_rs = view_record_year.executeQuery();

            if(view_given_year_rs.next()){
            while (view_given_year_rs.next()) {
                
                String orderNumber = view_given_year_rs.getString("od.orderNumber");
                String quantityOrder = view_given_year_rs.getString("od.quantityOrdered");
                String orderLineNumber = view_given_year_rs.getString("od.orderLineNumber");
                String orderDate = view_given_year_rs.getString("o.orderDate");
                String customerNumber = view_given_year_rs.getString("o.customerNumber");

                System.out.println("\nCustomer: " + customerNumber);
                System.out.println("Order Number: "+ orderNumber);
                System.out.println("Quantity Ordered" + quantityOrder);
                System.out.println("Order Line Number: " + orderLineNumber);
                System.out.println("Date Order made: " + orderDate + "\n");
                
                System.out.println("List generated... going back to main menu\n");

            }
        }
            else{
                System.out.println("No orders made during this year with this product\n");
            }
            
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }




    }


    private static int maxOrderNumber(){


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
            );
        String max_order = "SELECT MAX(orderNumber) AS max_order FROM orders";
        try(PreparedStatement max_order_ps = con.prepareStatement(max_order)){
            try(ResultSet max_order_rs = max_order_ps.executeQuery()){
            if(max_order_rs.next()){
             int maxOrder = max_order_rs.getInt("max_order");
                if(maxOrder == 0){
                    return 9999;
                }
             return maxOrder + 1;
            }
             }
        }
     con.close();
        
        } catch(Exception e){System.out.println(e);}
        return 9999;
        }


    public static void order_create(){ // to be chasned

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
            );
            con.setAutoCommit(false);  
        
        int         orderNumber = maxOrderNumber();
        String      product_code;
        int         quantityOrdered;
        double      priceEach;
        short         orderLineNumber;
        LocalDate   reqDate;
        LocalDate   orderDate;
        String      status = "In Process";
        String      comments;
        int         customerNum = 0;
      
        int         product_code_loop = 1;
   
        String      query_product;
       
        orderDate = LocalDate.now();
        reqDate = orderDate.plusDays(15);
        


        do{
            System.out.print("Enter product code: ");
            product_code = sc.nextLine();

            String query = "SELECT * FROM products WHERE productCode = " + product_code;
            try(PreparedStatement query_ps = con.prepareStatement(query)){
                try(ResultSet query_rs = query_ps.executeQuery()){
                    if(query_rs.next()){
                        query_product = query_rs.getString("productCode");
                        if(product_code == query_product){
                            System.out.println("Found product");
                        }
                        product_code_loop = 0;
                    }
                }catch(Exception e){System.out.println(e.getMessage());}
            }catch(Exception e){System.out.println(e.getMessage());}
        }while(product_code_loop == 1);



        System.out.print("Enter quantity: ");
        quantityOrdered = sc.nextInt();
        System.out.print("Enter price each:" );
        priceEach = sc.nextDouble();
        System.out.print("Enter Order Line Number: ");
        orderLineNumber = sc.nextShort();

        sc.nextLine();
        System.out.print("Enter comments: ");
        comments = sc.nextLine();
        System.out.print("Enter customer: ");
        customerNum = sc.nextInt();

        System.out.print("Finalize order? ");
        char y_or_n = sc.next().charAt(0);
        if(y_or_n == 'y' || y_or_n == 'Y'){
            String query_final_order = "INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?, ?)";
            try(PreparedStatement query_pst = con.prepareStatement(query_final_order)){
                query_pst.setInt(1, orderNumber);
                query_pst.setString(2, orderDate.toString());
                query_pst.setString(3, reqDate.toString());
                query_pst.setString(4, null);
                query_pst.setString(5, status);
                query_pst.setString(6, comments);
                query_pst.setInt(7, customerNum);
                if(query_pst.executeUpdate() == 1){
                }
                else{
                    System.out.println("Order did not create");
                    con.rollback();
                }
                query_final_order = "INSERT INTO orderdetails VALUES (?, ?, ?, ?, ?)";
                try(PreparedStatement query_pst_od = con.prepareStatement(query_final_order)){
                    query_pst_od.setInt(1, orderNumber);
                    query_pst_od.setString(2, product_code);
                    query_pst_od.setInt(3, quantityOrdered);
                    query_pst_od.setDouble(4, priceEach);
                    query_pst_od.setShort(5, orderLineNumber);
                    if(query_pst_od.executeUpdate() == 1){
                        System.out.println("Order created");
                        con.commit();
                    }
                    else{
                        System.out.println("Order failed");
                        con.rollback();
                    }
                }catch(SQLException e){System.err.println(e.getMessage());}
            }catch(SQLException e){System.err.println(e.getMessage());}

        }
        else{
            con.rollback();
        }
        con.close();

        } catch(SQLException e){System.err.println("SQL Exception:");
        System.err.println("Message: " + e.getMessage());
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error Code: " + e.getErrorCode());
        System.err.println("Stack Trace:");e.printStackTrace();
} catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

    } // end of create new order
    


    public static void update_orderdetails(String column, String update, int ordernum, int datatype){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
            );
         
            String query_update = "UPDATE orderdetails SET " + column + " = ? WHERE orderNumber = ?";
            try(PreparedStatement query_ps = con.prepareStatement(query_update)){
                switch(datatype){
                    case 1: query_ps.setString(1, update);
                            break;
                    case 2: query_ps.setShort(1, Short.parseShort(update));
                            break;
                    case 3: query_ps.setDouble(1, Double.parseDouble(update));
                            break;
                    case 4: query_ps.setInt(1, Integer.parseInt(update));
                            break;
                }
                query_ps.setInt(2, ordernum);
                query_ps.executeUpdate();
                if(query_ps.executeUpdate() == 1){
                    System.out.println("Order updated");
                 
                }
                con.close();
            } catch (SQLException e) {
                System.err.println("error updating " + e.getMessage());
                System.err.println(e.getErrorCode());
            }
        

        
      
        } catch(Exception e){System.out.println(e.getMessage());}
        }
    
    public static void update_orderstable(String column, String update, int ordernum, int datatype){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
            );
            String query_update = "UPDATE orders SET " + column + " = ? WHERE orderNumber = ?";
            try(PreparedStatement query_ps = con.prepareStatement(query_update)){
                switch(datatype){
                    case 1: query_ps.setString(1, update);
                    case 2: query_ps.setShort(1, Short.parseShort(update));
                    case 3: query_ps.setDouble(1, Double.parseDouble(update));
                    case 4: query_ps.setInt(1, Integer.parseInt(update));
                }
                query_ps.setInt(2, ordernum);
            
                if(query_ps.executeUpdate() == 1){
                   
                    System.out.println("Order updated");
                }
                else{
                    System.out.println("Did not update");
                }
                con.close();
            } catch (Exception e) {

            }
        

        
      
        } catch(Exception e){System.out.println(e);}
        }
    
    
    public static void delete_order(){
        int ordernumber;
        int stopper = 1;
        String product_code = null;
        String product_code_input;
        boolean isValid = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbsales","root","12345"
                );

        do{
        System.out.print("Enter order number: ");
        ordernumber = sc.nextInt();
        
        String ordernumber_find = "SELECT * FROM orders WHERE orderNumber = " + ordernumber + " AND status = 'In Process'";
          try(PreparedStatement ordernumber_ps = con.prepareStatement(ordernumber_find)){
            ResultSet ordernumber_rs = ordernumber_ps.executeQuery();
                if(ordernumber_rs.next()){
                    stopper = 0;
                }
          }catch(SQLException e){e.printStackTrace();}

        }while(stopper == 1);
        

  
        String select_product = "SELECT productCode FROM orders o LEFT JOIN orderdetails od ON o.orderNumber = od.orderNumber WHERE o.orderNumber = " + ordernumber;
        try(PreparedStatement select_product_ps = con.prepareStatement(select_product)){
            try(ResultSet select_rs = select_product_ps.executeQuery()){
                
                while(select_rs.next()){
                    product_code = select_rs.getString("productCode");
                    System.out.println("Products under the order ");
                    System.out.println(product_code);

                }
            }catch(SQLException e){System.err.println(e.getMessage()); System.err.println(e.getErrorCode());}

        }catch (SQLException e){System.err.println(e.getMessage()); System.err.println(e.getErrorCode());}
   
        do{
            sc.nextLine();
            System.out.print("Select product to delete: ");
            product_code_input = sc.nextLine();
            if(product_code.equals(product_code_input)){
                isValid = true;
                break;
            }
        }while(isValid = false);





        String deletequery = "UPDATE orderdetails SET productCode = ? WHERE orderNumber = ?";
        try(PreparedStatement deletequery_ps = con.prepareStatement(deletequery)){
            deletequery_ps.setString(1, null);
            deletequery_ps.setInt(2, ordernumber);
            if(deletequery_ps.executeUpdate() == 1){
                System.out.println("Deleted a product");
            }
            else{
                System.out.println("Error deleting product");
            }

        }catch(Exception e){System.err.println(e.getMessage());}

    } catch(SQLException e){System.err.println("SQL Exception:");
            System.err.println("Message: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Stack Trace:");e.printStackTrace();
            }catch (ClassNotFoundException e) {
        
                e.printStackTrace();
            }
}



   public static void update_order_product(){

    int ordernumber;
    String product_code_input;
    String product_code_select = null;
    int stopper = 1;
    int stopper_2  = 1;
    int order_number_select;

    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/dbsales","root","12345"
            );
        do{
        System.out.print("Enter order number: ");
        ordernumber = sc.nextInt();
    
        String if_ordernumber_exists = "SELECT o.orderNumber FROM orders o LEFT JOIN orderdetails od ON o.orderNumber = od.orderNumber WHERE o.orderNumber = " + ordernumber +" AND status = 'In Process'";
        try(PreparedStatement if_on_exists_ps = con.prepareStatement(if_ordernumber_exists)){
            try(ResultSet if_on_exists_rs = if_on_exists_ps.executeQuery()){
            if(if_on_exists_rs.next()){
                order_number_select = if_on_exists_rs.getInt("orderNumber");
                if(ordernumber == order_number_select){
                    System.out.println("Order number exists");
                    stopper = 0;
                }
                }
             }catch(Exception e){System.err.println(e.getMessage());}
        }catch(Exception e){System.err.println(e.getMessage());}
        }while(stopper == 1);
        
        String show_product = "SELECT * FROM orders o LEFT JOIN orderdetails od ON o.orderNumber = od.orderNumber WHERE o.orderNumber = ?" ;
        try(PreparedStatement show_ps = con.prepareStatement(show_product)){
            show_ps.setInt(1, ordernumber);
            try(ResultSet show_rs = show_ps.executeQuery()){
                if(show_rs.next()){
                    String product_code_before = show_rs.getString("productCode");
                    System.out.println("Product code: " + product_code_before);
                }
            }catch(Exception e){System.err.println(e.getMessage());}
        }catch(Exception e){System.err.println(e.getMessage());}


        do{
            System.out.print("Enter new product code: ");
            sc.nextLine();
            product_code_input = sc.nextLine();
            String if_product_exists = "SELECT od.productCode FROM orders o LEFT JOIN orderdetails od ON o.orderNumber = od.orderNumber AND od.orderNumber = " + ordernumber;
            try(PreparedStatement if_product_exists_ps = con.prepareStatement(if_product_exists)){
                try(ResultSet if_product_exists_rs = if_product_exists_ps.executeQuery()){
                    if(if_product_exists_rs.next()){
                        product_code_select = product_code_input;
                        stopper_2 = 0;
                    }
                }catch(Exception e){System.err.println(e.getMessage());}
            }catch(Exception e){System.err.println(e.getMessage());}
        }while(stopper_2 == 1);


        String update_order_product = "UPDATE orderdetails SET productCode = ? WHERE orderNumber = ?";
        try(PreparedStatement update_order_product_ps = con.prepareStatement(update_order_product)){
            update_order_product_ps.setString(1, product_code_input);
            update_order_product_ps.setInt(2, ordernumber);
            if(update_order_product_ps.executeUpdate()== 1){
                System.out.println("Updated successfully");
            }
            else{
                System.out.println("Did not update... going back to main menu");
                con.close();
            }
        }catch(Exception e){System.err.println(e.getMessage());}

   }catch(Exception e){System.err.println(e.getMessage());}
    
   }


    public static void update_order(){
        int orderNumber ;
        
        int index;
    
        int stoploop = 0;

    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/dbsales","root","12345"
            );
         
        do{
          System.out.print("Enter order number: ");
          orderNumber = sc.nextInt();

          String ordernumber_find = "SELECT * FROM orders WHERE orderNumber = " + orderNumber + " AND status = 'In Process' ";
          try(PreparedStatement ordernumber_ps = con.prepareStatement(ordernumber_find)){
            ResultSet ordernumber_rs = ordernumber_ps.executeQuery();
                if(ordernumber_rs.next()){
                    stoploop = 1;
                }
          }catch(SQLException e){e.printStackTrace();}
        }while(stoploop == 0);  

        String query = "SELECT * FROM orders o LEFT JOIN orderdetails od ON o.orderNumber = od.orderNumber WHERE o.orderNumber= ?";
        try(PreparedStatement query_ps = con.prepareStatement(query)){
            query_ps.setInt(1, orderNumber);
            try(ResultSet query_rs = query_ps.executeQuery()){
                if(query_rs.next()){
                    System.out.println("Current Details");
                    System.out.println("Order Number: " + query_rs.getInt("orderNumber"));
                    System.out.println("Customer Code: "+ query_rs.getInt("customerNumber"));
                    System.out.println("1. Product Code: " + query_rs.getString("productCode"));
                    System.out.println("2. Quantity: " + query_rs.getInt("quantityOrdered"));
                    System.out.println("3. Price Each: " + query_rs.getDouble("priceEach"));
                    System.out.println("4. Order Line Number: " + query_rs.getInt("orderLineNumber"));
                    System.out.println("5. Status: " + query_rs.getString("status"));
                    System.out.println("6. Comments: " + query_rs.getString("comments"));
                
                    
                        System.out.print("Select what to update: ");
                        index = sc.nextInt();
                  
                    switch(index){
                        case 1:
                            System.out.print("Enter product: ");
                            String product_code = sc.nextLine();
                            String product_update = String.valueOf(product_code);
                            update_orderdetails("productCode", product_update, orderNumber, 1);

                            break;
                        case 2:
                            System.out.print("Enter quantity: ");
                            int quantity = sc.nextInt();
                            String quantity_update = String.valueOf(quantity);
                            update_orderdetails("quantityOrdered", quantity_update, orderNumber, 4);
                
                            break;
                        case 3:
                            System.out.print("Enter price each: ");
                            Double price = sc.nextDouble();
                            String price_udpate = String.valueOf(price);
                            update_orderdetails("priceEach", price_udpate, orderNumber, 3);
                    
                            break;
                        case 4:
                            System.out.print("Enter order line number: ");
                            int orderline = sc.nextInt();
                            String orderline_update = String.valueOf(orderline);
                            update_orderdetails("orderLineNumber", orderline_update, orderNumber, 4);
                            
                            break;
                        case 5:
                            System.out.print("Enter status: ");
                            sc.nextLine();
                            String status = sc.nextLine();
                            String status_update = String.valueOf(status);
                            update_orderstable("status", status_update, orderNumber, 1);
                           
                            break;
                        case 6:
                            System.out.print("Enter comments: ");
                            sc.nextLine();
                            String comments = sc.nextLine();
                            String comments_update = String.valueOf(comments);
                            update_orderstable("comments", comments_update, orderNumber, 1);
                            break;
                    }
                    
                con.close();
                
                }
            }
        }
        
    } catch(SQLException e){System.err.println("SQL Exception:");
    System.err.println("Message: " + e.getMessage());
    System.err.println("SQL State: " + e.getSQLState());
    System.err.println("Error Code: " + e.getErrorCode());
    System.err.println("Stack Trace:");e.printStackTrace();
    }catch (ClassNotFoundException e) {

        e.printStackTrace();
    }
    }
    
     public static void office_insert() {

        int stopper = 1;

        do {

            try {

                // input office details

                System.out.print("Enter office code: ");

                String office_code = sc.nextLine();

                System.out.print("Enter city: ");

                String office_city = sc.nextLine();

                System.out.print("Enter phone: ");

                String office_phone = sc.nextLine();

                System.out.print("Enter address line 1: ");

                String office_addressline1 = sc.nextLine();

                System.out.print("Enter address line 2: ");

                String office_addressline2 = sc.nextLine();

                System.out.print("Enter state: ");

                String office_state = sc.nextLine();

                System.out.print("Enter country: ");

                String office_country = sc.nextLine();

                System.out.print("Enter postal code: ");

                String office_postalcode = sc.nextLine();

                System.out.print("Enter territory: ");

                String office_territory = sc.nextLine();

    

                // establish connection

                Class.forName("com.mysql.cj.jdbc.Driver");

                Connection con = DriverManager.getConnection(

                        "jdbc:mysql://localhost:3306/dbsales", "admin", "DLSU1234"

                );

    

                PreparedStatement stmt;

                String query = "INSERT INTO offices " +

                        "(officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory) " +

                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                stmt = con.prepareStatement(query);

                stmt.setString(1, office_code);

                stmt.setString(2, office_city);

                stmt.setString(3, office_phone);

                stmt.setString(4, office_addressline1);

                stmt.setString(5, office_addressline2);

                stmt.setString(6, office_state);

                stmt.setString(7, office_country);

                stmt.setString(8, office_postalcode);

                stmt.setString(9, office_territory);

    

                int rowsInserted = stmt.executeUpdate();

    

                // check if successful

                if (rowsInserted > 0) {

                    System.out.println("Office created successfully");

                } else {

                    System.out.println("Failed to create office");

                }

    

                // close resources

                stmt.close();

                con.close();

            } catch (ClassNotFoundException e) {

                System.out.println("Mysql jdbc driver not found!");

                e.printStackTrace();

            } catch (SQLException e) {

                System.out.println("Database connection error.");

                e.printStackTrace();

            }

    

            // Ask if the user wants to create another office 

            char insert_loop;

            do {

                System.out.print("Do you want to create another office (Y/N): ");

                insert_loop = Character.toUpperCase(sc.next().charAt(0));

                if (insert_loop == 'N' || insert_loop == 'Y') {

                    sc.nextLine(); // consume newline

                    break;

                } else {

                    System.out.println("Invalid input! Please enter Y or N.");

                }

            } while (true);

    

            if (insert_loop == 'N') {

                stopper = 0;

            }

        } while (stopper == 1);

    }

    

    public static void office_update() {

        int stopper = 1;

        do {

            try { // establish connection

                Class.forName("com.mysql.cj.jdbc.Driver");

                Connection con = DriverManager.getConnection(

                        "jdbc:mysql://localhost:3306/dbsales", "admin", "DLSU1234"

                );

    

                System.out.print("Enter office code: ");

                String office_code = sc.next();

    

                // check if the office exists

                String query_check = "SELECT * FROM offices WHERE officeCode = ?";

                PreparedStatement ps_check = con.prepareStatement(query_check);

                ps_check.setString(1, office_code);

    

                ResultSet rs_check = ps_check.executeQuery();

    

                if (rs_check.next()) {

                    System.out.println("Enter the column you want to update (1-8) or 0 to skip:");

                    if (sc.hasNextInt()) {

                        int col_choice = sc.nextInt();

                        sc.nextLine(); // consume newline

    

                        if (col_choice >= 1 && col_choice <= 8) {

                            String col_name = "";

                            switch (col_choice) {

                                case 1:

                                    col_name = "city";

                                    break;

                                case 2:

                                    col_name = "phone";

                                    break;

                                case 3:

                                    col_name = "addressLine1";

                                    break;

                                case 4:

                                    col_name = "addressLine2";

                                    break;

                                case 5:

                                    col_name = "state";

                                    break;

                                case 6:

                                    col_name = "country";

                                    break;

                                case 7:

                                    col_name = "postalCode";

                                    break;

                                case 8:

                                    col_name = "territory";

                                    break;

                            }

    

                            if (!col_name.isEmpty()) {

                                System.out.println("Old value: " + rs_check.getString(col_name));

                                System.out.print("Enter new value: ");

                                String new_value = sc.nextLine();

    

                                String update_query = "UPDATE offices SET " + col_name + " = ? WHERE officeCode = ?";

                                PreparedStatement ps_update = con.prepareStatement(update_query);

                                ps_update.setString(1, new_value);

                                ps_update.setString(2, office_code);

    

                                int rows_affected = ps_update.executeUpdate();

    

                                if (rows_affected > 0) {

                                    System.out.println("Office record updated successfully!\n");

                                } else {

                                    System.out.println("Failed to update office record\n");

                                }

                                ps_update.close();

                            }

                        } else {

                            System.out.println("Invalid choice!");

                        }

                    } else {

                        System.out.println("Invalid input! Please enter a number.");

                        sc.next(); // consume invalid input

                    }

                } else {

                    System.out.println("Office with code " + office_code + " does not exist!\n");

                }

    

                ps_check.close();

                rs_check.close();

    

                char continue_choice;

                do {

                    System.out.print("Do you want to update another office record? (Y/N): ");

                    continue_choice = Character.toUpperCase(sc.next().charAt(0));

                    if (continue_choice == 'N') {

                        stopper = 0;

                    } else if (continue_choice == 'Y') {

                        stopper = 1;

                    } else {

                        System.out.println("Invalid input! Please enter Y or N.");

                    }

                } while (continue_choice != 'Y' && continue_choice != 'N');

            } catch (ClassNotFoundException e) {

                System.out.println("My sql driver not found!");

                e.printStackTrace();

            } catch (SQLException e) {

                System.out.println("Database connection error.");

                e.printStackTrace();

            }

        } while (stopper == 1);

    }

    

    public static void office_delete() {

        try { // establish connection

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(

                "jdbc:mysql://localhost:3306/dbsales", "admin", "DLSU1234"

            );

    

            int stopper = 1;

            do {

                System.out.print("Enter the office code to delete: ");

                String officeCode = sc.next();

    

                // sql query to delete the record with the specified office code

                String deleteQuery = "DELETE FROM offices WHERE officeCode = ?";

                PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);

                deleteStatement.setString(1, officeCode);

    

                try {

                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {

                        System.out.println("Record with office code " + officeCode + " deleted successfully!");

                    } else {

                        System.out.println("No record found with office code" + officeCode);

                    }

                } catch (SQLException e) {

                    // check if the error code indicates referencing

                    if (e.getErrorCode() == 1451) {

                        System.out.println("Error!! Cannot delete record because it is being referenced by other records");

                    } else {

                        System.out.println(e.getMessage());

                    }

                }

    

                char deleteLoop;

                do {

                    System.out.print("\nDo you want to delete another record? (Y/N): ");

                    deleteLoop = Character.toUpperCase(sc.next().charAt(0));

                    if (deleteLoop == 'Y' || deleteLoop == 'N') {

                        break;

                    } else {

                        System.out.println("Invalid input! Please enter Y or N.");

                    }

                } while (true);

    

                if (deleteLoop == 'N') {

                    stopper = 0;

                }

    

            } while (stopper == 1);

    

            con.close();

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

        }

    }

    

    public static void office_viewemployees() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(

                    "jdbc:mysql://localhost:3306/dbsales", "admin", "DLSU1234"

            );

        

            // prompt user to enter the office code

            System.out.print("Enter the office code to view: ");

            String officeCode = sc.next();

        

            // sql query to retrieve employees assigned to the office

            String employeesQuery = "SELECT employeeNumber FROM employees WHERE officeCode = ?";

            PreparedStatement employeesPs = con.prepareStatement(employeesQuery);

            employeesPs.setString(1, officeCode);

        

            ResultSet employeesRs = employeesPs.executeQuery();

        

            // display the list of employee codes assigned to the office

            System.out.println("Employees assigned to office with code " + officeCode + ":");

            while (employeesRs.next()) {

                System.out.println("Employee Number: " + employeesRs.getString("employeeNumber"));

            }

        

            employeesRs.close();

            employeesPs.close();

            con.close();

            } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

        }

    }

        

    public static void generate_salesreport() {

        try { // connection

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales","admin","DLSU1234")) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  // format in database

        

                boolean isValid = false;

                int year = 0;

                int month = 0;

        

                while (!isValid) {

                    // prompts user for month and year

                    System.out.print("Enter the year: ");

                    year = sc.nextInt();

                    System.out.print("Enter the month (1-12): ");

                    month = sc.nextInt();

        

                    // Check if there are records in the database for the specified month and year

                    String query = "SELECT COUNT(*) AS count FROM orders WHERE MONTH(orderDate) = ? AND YEAR(orderDate) = ?";

                    try (PreparedStatement stmt = con.prepareStatement(query)) {

                        stmt.setInt(1, month);

                        stmt.setInt(2, year);

                        try (ResultSet rs = stmt.executeQuery()) {

                            rs.next();

                            isValid = rs.getInt("count") > 0;

                            if (!isValid) {

                                System.out.println("No records found for the given month and year. Please try again!");

                            }

                        }

                    }

                }

        

                // Generate sales report

                generateSalesReport(con, year, month);

            }

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();

        }

    }

        

    private static void generateSalesReport(Connection con, int year, int month) throws SQLException {

        // Format the start and end dates for the month

        String startDate = year + "-" + String.format("%02d", month) + "-01 00:00:00";

        int lastDayOfMonth = get_lastDay(year, month);

        String endDate = year + "-" + String.format("%02d", month) + "-" + lastDayOfMonth + " 23:59:59";

        

        // SQL query

        String query = "SELECT o.orderDate, p.productName, SUM(od.quantityOrdered * p.buyPrice) AS total_sales " +

                        "FROM orders o " +

                        "JOIN orderdetails od ON o.orderNumber = od.orderNumber " +

                        "JOIN products p ON od.productCode = p.productCode " +

                        "WHERE o.orderDate BETWEEN ? AND ? " +

                        "GROUP BY o.orderDate, p.productName";

    

        try (PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, startDate);

            stmt.setString(2, endDate);

            try (ResultSet rs = stmt.executeQuery()) {

                // Print the sales report

                System.out.println("Sales Report for " + year + "-" + String.format("%02d", month) + ":");

                System.out.println("-----------------------------------------------------------------------");

                System.out.printf("%-20s | %-30s | %-15s%n", "Order Date", "Product Name", "Total Sales");

                System.out.println("-----------------------------------------------------------------------");

    

                double totalSalesOverall = 0;

    

                while (rs.next()) {

                    String orderDate = rs.getTimestamp("orderDate").toString();

                    String productName = rs.getString("productName");

                    double totalSales = rs.getDouble("total_sales");

    

                    System.out.printf("%-20s | %-30s | %-15.2f%n", orderDate, productName, totalSales);

                    totalSalesOverall += totalSales; // Total sales

                }

    

                System.out.println("-----------------------------------------------------------------------");

                System.out.println("Overall Total Sales: $" + String.format("%.2f", totalSalesOverall));

            }

        }

    }    

            

    private static int get_lastDay(int year, int month) {

        switch (month) {

            case 1, 3, 5, 7, 8, 10, 12:

                return 31;

            case 4, 6, 9, 11:

                return 30;

            case 2:

                if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {

                    return 29; // Leap year

                } else {

                    return 28;

                }

            default:

                return -1; // Invalid month

        }

    }  
}


